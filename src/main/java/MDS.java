/**
 * Maximum Distance Separable Matrix
 *
 * References:
 * https://en.wikipedia.org/wiki/MDS_matrix
 */
public class MDS {

    private static final byte[][] MDS = {
            { 0x02, 0x03, 0x01, 0x01 },
            { 0x01, 0x02, 0x03, 0x01 },
            { 0x01, 0x01, 0x02, 0x03 },
            { 0x03, 0x01, 0x01, 0x02 }
    };

    private static final byte[][] MDS_INV = {
            { 0x0E, 0x0B, 0x0D, 0x09 },
            { 0x09, 0x0E, 0x0B, 0x0D },
            { 0x0D, 0x09, 0x0E, 0x0B },
            { 0x0B, 0x0D, 0x09, 0x0E }
    };

    public static byte[][] getMDS() {
        return MDS;
    }

    public static byte[][] getInvMDS() {
        return MDS_INV;
    }

}
