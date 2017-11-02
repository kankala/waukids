package kr.co.rmtechs.waukids.scanner.attendance.job;

import kr.co.rmtechs.waukids.scanner.attendance.service.StudentGroup;
import kr.co.rmtechs.waukids.scanner.attendance.service.StudentService;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class AbstractAbsenceJob implements Job {

    protected StudentGroup groups;

    protected StudentService service;

    protected int schoolId;
    protected int repeat;

    protected Date endTime;

    @Override
    public void execute(final JobExecutionContext context) throws JobExecutionException {
        absence();
    }

    public abstract void absence();

    public void setSchoolId(final int schoolId) {
        this.schoolId = schoolId;
    }

    public void setRepeat(final int repeat) {
        this.repeat = repeat;
    }

    public void setEndTime(final Date endTime) {
        this.endTime = endTime;
    }

    public void setGroups(final StudentGroup groups) {
        this.groups = groups;
    }

    public void setService(final StudentService service) {
        this.service = service;
    }
}
