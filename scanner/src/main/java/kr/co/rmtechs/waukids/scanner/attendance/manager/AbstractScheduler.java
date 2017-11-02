package kr.co.rmtechs.waukids.scanner.attendance.manager;

import static org.quartz.DateBuilder.todayAt;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Date;

import javax.annotation.Resource;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import kr.co.rmtechs.waukids.scanner.attendance.bean.AttendanceConfigVO;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentGroup;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentService;
import kr.co.rmtechs.waukids.scanner.base.service.BaseService;

public abstract class AbstractScheduler implements KindergartenManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String name = this.getClass().getName();

    @Autowired
    @Resource(name = "attendanceConfigService")
    protected BaseService<Integer, AttendanceConfigVO> configService;

    private Scheduler scheduler;

    @Override
    public void initialize() {
        final SchedulerFactory schedulerFactory = new StdSchedulerFactory();

        try {
            scheduler = schedulerFactory.getScheduler();

            registerSchedule();

            scheduler.start();
        } catch (final SchedulerException e) {
            logger.error("scheduler : " + e);
        }
    }

    private void registerSchedule() {
        for (final AttendanceConfigVO config : configService.lists()) {
            registerAbsenceSchedule(config);
        }
    }

    private void registerAbsenceSchedule(final AttendanceConfigVO config) {
        final Date startTime = getStartTime(config);
        final Date endTime = getEndTime(config);

        final Class<? extends Job> jobClass = getJobClass();

        addSchedule(config, startTime, endTime, jobClass);
    }

    private void addSchedule(final AttendanceConfigVO config, final Date startTime, final Date endTime,
            final Class<? extends Job> jobClass) {

        final int schoolId = config.getSchoolId();
        final int interval = getInterval(config);
        final int repeatCount = getRepeat(config);

        final String jobName = "job" + name + schoolId;
        final String groupName = "group" + name + schoolId;

        final JobDetail job = newJob(jobClass).withIdentity(jobName, groupName).build();

        createJobData(schoolId, config.getRepeat(), getTime(config.getInEndTime()), job);

        final String triggerName = "trigger" + name + schoolId;

        final SimpleTrigger trigger = newTrigger().withIdentity(triggerName, groupName).startAt(startTime)
                .endAt(endTime).withSchedule(simpleSchedule().withIntervalInSeconds(interval)
                        .withRepeatCount(repeatCount).withMisfireHandlingInstructionNowWithExistingCount())
                .build();
        try {
            scheduler.scheduleJob(job, trigger);

            logger.info("Absence Manager : " + config);
        } catch (final SchedulerException e) {
            logger.error("absence scheduler : " + e);
        }
    }

    public abstract Date getStartTime(AttendanceConfigVO config);

    public abstract Date getEndTime(AttendanceConfigVO config);

    public abstract int getInterval(AttendanceConfigVO config);

    public abstract int getRepeat(AttendanceConfigVO config);

    public abstract Class<? extends Job> getJobClass();

    @Autowired
    private StudentGroup groups;

    @Autowired
    @Resource(name = "studentService")
    private StudentService studentService;

    private void createJobData(final int schoolId, final int repeat, final Date endTime, final JobDetail job) {
        final JobDataMap jobDataMap = job.getJobDataMap();

        jobDataMap.put("schoolId", schoolId);
        jobDataMap.put("repeat", repeat);
        jobDataMap.put("endTime", endTime);
        jobDataMap.put("groups", groups);
        jobDataMap.put("service", studentService);
    }

    private static final int MINUTE = 60;

    protected Date getTime(final String time) {
        return getTime(time, 0);
    }

    protected Date getTime(final String time, final int delayMinute) {
        final String[] times = time.split(":");

        int hour = Integer.valueOf(times[0]);
        int minute = Integer.valueOf(times[1]) + delayMinute;

        if (minute >= MINUTE) {
            hour += 1;
            minute -= MINUTE;
        } else if (minute < 0) {
            hour -= 1;
            minute += MINUTE;
        }

        return todayAt(hour, minute, 0);
    }

    @Override
    public void shutdown() {
        try {
            if (scheduler != null) {
                scheduler.shutdown();
            }
        } catch (final SchedulerException e) {
            logger.error("scheduler shutdown error : " + e);
        }
    }
}
