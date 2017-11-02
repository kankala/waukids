package kr.co.rmtechs.waukids.scanner.scanner.beacon;

import kr.co.rmtechs.waukids.scanner.util.ByteUtil;

import java.util.Arrays;

public final class Scanner {
    public static final byte SYNC = (byte) 0xF7;
    private static final byte ETX = (byte) 0xFE;

    public static final byte CMD_SCAN = (byte) 0x4E;
    private static final byte CMD_STATUS = (byte) 0x37;

    private static final byte ZERO = (byte) 0x30;
    private static final byte ONE = (byte) 0x31;

    private Scanner() {
    }

    public static Beacon parse(final byte[] data) {
        final int length = ByteUtil.toInt(Arrays.copyOfRange(data, 1, 5));

        final Beacon beacon = new Beacon();

        beacon.parse(Arrays.copyOfRange(data, 6, length + 6));

        return beacon;
    }

    public static byte[] getStatusMessage() {
        final byte[] bytes = new byte[7];

        bytes[0] = SYNC;
        bytes[1] = ZERO;
        bytes[2] = ZERO;
        bytes[3] = ZERO;
        bytes[4] = ONE;
        bytes[5] = CMD_STATUS;
        bytes[6] = ETX;

        return bytes;

    }
}
