package kr.co.rmtechs.waukids.scanner.scanner.beacon;

public class Beacon extends IBeacon {
    private String scannerAddress;

    public String getScannerAddress() {
        return scannerAddress;
    }

    public void setScannerAddress(String scannerAddress) {
        this.scannerAddress = scannerAddress;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((scannerAddress == null) ? 0 : scannerAddress.hashCode());
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
        final Beacon other = (Beacon) obj;
        if (scannerAddress == null) {
            if (other.scannerAddress != null) {
                return false;
            }
        }
        else if (!scannerAddress.equals(other.scannerAddress)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("Beacon [scannerAddress=%s]", scannerAddress);
    }

}
