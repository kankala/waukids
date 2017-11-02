package kr.co.rmtechs.waukids.scanner.attendance.bean;

import java.io.Serializable;

public class AttendanceConfigVO implements Serializable {
    private static final long serialVersionUID = -3518670287397573980L;

    private int id;
    private int schoolId;

    private String inStartTime;
    private String inEndTime;

    private String outStartTime;
    private String outEndTime;

    private int interval;
    private int repeat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public String getInStartTime() {
        return inStartTime;
    }

    public void setInStartTime(String inStartTime) {
        this.inStartTime = inStartTime;
    }

    public String getInEndTime() {
        return inEndTime;
    }

    public void setInEndTime(String inEndTime) {
        this.inEndTime = inEndTime;
    }

    public String getOutStartTime() {
        return outStartTime;
    }

    public void setOutStartTime(String outStartTime) {
        this.outStartTime = outStartTime;
    }

    public String getOutEndTime() {
        return outEndTime;
    }

    public void setOutEndTime(String outEndTime) {
        this.outEndTime = outEndTime;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return String.format(
                "AttendanceConfigVO [id=%s, schoolId=%s, inStartTime=%s, inEndTime=%s, outStartTime=%s, outEndTime=%s, interval=%s, repeat=%s]",
                id, schoolId, inStartTime, inEndTime, outStartTime, outEndTime, interval, repeat);
    }

}
