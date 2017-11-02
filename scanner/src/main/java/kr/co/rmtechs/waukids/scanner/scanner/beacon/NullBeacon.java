package kr.co.rmtechs.waukids.scanner.scanner.beacon;

public class NullBeacon extends Beacon {

    @Override
    public String getMacAddress() {
        return String.valueOf("");
    }
}
