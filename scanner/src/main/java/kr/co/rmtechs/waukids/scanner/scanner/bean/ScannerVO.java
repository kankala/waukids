package kr.co.rmtechs.waukids.scanner.scanner.bean;

import java.io.Serializable;

public class ScannerVO implements Serializable {
    private static final long serialVersionUID = 3340709766683822393L;

    private int id;
    private int schoolId;

    private String ipAddress;
    private int port;

    private boolean isUsed;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
}
