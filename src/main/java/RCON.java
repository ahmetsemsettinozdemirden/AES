import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

/**
 * Round constants
 *
 * References:
 * https://en.wikipedia.org/wiki/AES_key_schedule
 */
public class RCON {

    private final static byte[] RCON = HexBin.decode("01020408102040801B366CD8AB4D9A2F5EBC63C697356AD4B37DFAEFC5");

    public static byte[] getValue(int i) {
        if (i < 0 || i >= 30)
            throw new IllegalArgumentException("parameter must be between [0, 30)");

        return new byte[]{RCON[i], 0x00, 0x00, 0x00};
    }

}
