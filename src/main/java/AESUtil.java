/**
 * Helper methods for AES implementation.
 */
public class AESUtil {

    /**
     * Print given byte array in hexadecimal format.
     * Generally used for debugging purposes.
     * @param arr byte array
     */
    public static void printHex(byte[] arr) {
        System.out.println();
        for (byte b: arr)
            System.out.print(String.format("%02X ", b));
        System.out.println();
    }

    /**
     * Galois Field (256) Multiplication of two Bytes
     * @param a first byte
     * @param b second byte
     * @return <code>a * b</code> multiplication of a and b
     */
    public static byte GMul(byte a, byte b) {
        byte p = 0;

        for (int counter = 0; counter < 8; counter++) {
            if ((b & 1) != 0) {
                p ^= a;
            }

            boolean hi_bit_set = (a & 0x80) != 0;
            a <<= 1;
            if (hi_bit_set) {
                a ^= 0x11B; /* x^8 + x^4 + x^3 + x + 1 */
            }
            b >>= 1;
        }

        return p;
    }

}
