package kr.co.rmtechs.waukids.scanner.attendance.manager;

import kr.co.rmtechs.waukids.scanner.attendance.bean.AttendanceConfigVO;
import kr.co.rmtechs.waukids.scanner.attendance.job.InAbsenceJob;

import java.util.Date;

import org.quartz.Job;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("inAbsenceManager")
public class InAbsenceImpl extends AbstractScheduler {

    @Value("${absence.initDelay:10}")
    private int ABSENCE_INIT_DELAY;

    @Value("${absence.repeat.times:2}")
    private int EXTRA_TIMES;

    @Override
    public Date getStartTime(final AttendanceConfigVO config) {
        return getTime(config.getInEndTime(), ABSENCE_INIT_DELAY);
    }

    @Override
    public Date getEndTime(final AttendanceConfigVO config) {
        return getTime(config.getOutStartTime(), ABSENCE_INIT_DELAY * -1);
    }

    @Override
    public int getInterval(final AttendanceConfigVO config) {
        return config.getInterval();
    }

    @Override
    public int getRepeat(final AttendanceConfigVO config) {
        return config.getRepeat() * EXTRA_TIMES;
    }

    @Override
    public Class<? extends Job> getJobClass() {
        return InAbsenceJob.class;
    }
}
