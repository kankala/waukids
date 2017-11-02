package kr.co.rmtechs.waukids.scanner.util;

public class ByteUtil {

    private ByteUtil() {
    }

    public static String toHexString(final byte[] bytes) {
        StringBuilder builder = new StringBuilder();

        for (final byte b : bytes) {
            builder.append(String.valueOf((char) b));
        }

        return builder.toString();
    }

    public static int toInt(final byte[] bytes) {
        return Integer.parseInt(new String(bytes));
    }
}
