package kr.co.rmtechs.waukids.scanner.attendance.manager;

import java.util.Date;

import org.quartz.Job;
import org.quartz.SimpleTrigger;
import org.springframework.stereotype.Service;

import kr.co.rmtechs.waukids.scanner.attendance.bean.AttendanceConfigVO;
import kr.co.rmtechs.waukids.scanner.attendance.job.OutAbsenceJob;

@Service("outAbsenceManager")
public class OutAbsenceImpl extends AbstractScheduler {

    @Override
    public Date getStartTime(final AttendanceConfigVO config) {
        return getTime(config.getOutStartTime(), 0);
    }

    @Override
    public Date getEndTime(final AttendanceConfigVO config) {
        return getTime(config.getOutEndTime(), 0);
    }

    @Override
    public int getInterval(final AttendanceConfigVO config) {
        return config.getInterval();
    }

    @Override
    public int getRepeat(final AttendanceConfigVO config) {
        return SimpleTrigger.REPEAT_INDEFINITELY;
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return OutAbsenceJob.class;
    }
}
