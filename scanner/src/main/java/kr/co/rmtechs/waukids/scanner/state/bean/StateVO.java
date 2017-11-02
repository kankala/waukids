package kr.co.rmtechs.waukids.scanner.state.bean;

public class StateVO {
    private int serialId;

    private int schoolId;
    private int studentId;

    private int code;

    private String message;

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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("StateVO [serialId=%s, schoolId=%s, studentId=%s, code=%s, message=%s]", serialId,
                schoolId, studentId, code, message);
    }
}
