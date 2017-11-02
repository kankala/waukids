package kr.co.rmtechs.waukids.scanner.scanner.beacon;

import kr.co.rmtechs.waukids.scanner.util.ByteUtil;

import java.util.Arrays;

public class IBeacon {

    private String macAddress;

    private String uuid;

    private int major;
    private int minor;

    private int rssi;

    private int battery = -1;

    private int txPower;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getMajor() {
        return major;
    }

    public void setMajor(int major) {
        this.major = major;
    }

    public int getMinor() {
        return minor;
    }

    public void setMinor(int minor) {
        this.minor = minor;
    }

    public int getRssi() {
        return rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getTxPower() {
        return txPower;
    }

    public void setTxPower(int txPower) {
        this.txPower = txPower;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + battery;
        result = prime * result + ((macAddress == null) ? 0 : macAddress.hashCode());
        result = prime * result + major;
        result = prime * result + minor;
        result = prime * result + rssi;
        result = prime * result + txPower;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final IBeacon other = (IBeacon) obj;
        if (battery != other.battery) {
            return false;
        }
        if (macAddress == null) {
            if (other.macAddress != null) {
                return false;
            }
        }
        else if (!macAddress.equals(other.macAddress)) {
            return false;
        }
        if (major != other.major) {
            return false;
        }
        if (minor != other.minor) {
            return false;
        }
        if (rssi != other.rssi) {
            return false;
        }
        if (txPower != other.txPower) {
            return false;
        }
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        }
        else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

    public void parse(final byte[] data) {
        macAddress = ByteUtil.toHexString(Arrays.copyOfRange(data, 0, 12));

        rssi = ByteUtil.toInt(Arrays.copyOfRange(data, 12, 15));

        final int length = ByteUtil.toInt(Arrays.copyOfRange(data, 15, 17));

        if (length != 0) {
            parseAdvertisement(Arrays.copyOfRange(data, 17, length + 17));
        }
    }

    private void parseAdvertisement(final byte[] data) {
        battery = Byte.valueOf(data[23]).intValue();
        txPower = Byte.valueOf(data[24]).intValue();
    }

    @Override
    public String toString() {
        return String.format("IBeacon [macAddress=%s, uuid=%s, major=%s, minor=%s, rssi=%s, battery=%s, txPower=%s]",
                macAddress, uuid, major, minor, rssi, battery, txPower);
    }
}
