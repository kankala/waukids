package kr.co.rmtechs.waukids.scanner.attendance.bean;

import java.io.Serializable;
import java.util.Date;

public class StudentVO implements Serializable {

    private static final long serialVersionUID = 8813307485619487114L;

    public static int STATE_NONE = -1;
    public static int STATE_ATTENDANCE = 0;
    public static int STATE_ABSENCE = 1;
    public static int STATE_LEAVE = 3;

    private int serialId;

    private int schoolId;
    private int classId;
    private int studentId;

    private String name;

    private String beaconId;

    private int attend = STATE_NONE;

    private int attendCount;

    private boolean isStay;

    private Date time = null;

    private int battery;

    public int getSerialId() {
        return serialId;
    }

    public void setSerialId(int serialId) {
        this.serialId = serialId;
    }

    public int getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    public int getClassId() {
        return classId;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }

    public int getAttend() {
        return attend;
    }

    public void setAttend(int attend) {
        this.attend = attend;
    }

    public int getAttendCount() {
        return attendCount;
    }

    public void setAttendCount(int attendCount) {
        this.attendCount = attendCount;
    }

    public boolean isStay() {
        return isStay;
    }

    public void setStay(boolean isStay) {
        this.isStay = isStay;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    @Override
    public String toString() {
        return String.format(
                "StudentVO [schoolId=%s, classId=%s, studentId=%s, name=%s, beaconId=%s, attend=%s, attendCount=%s, isStay=%s, time=%s, battery=%s]",
                schoolId, classId, studentId, name, beaconId, attend, attendCount, isStay, time, battery);
    }
}
