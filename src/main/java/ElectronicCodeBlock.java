import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Electronic Code Block method for encrypting plaintext of String.
 *
 * Usage:
 * <code>ElectronicCodeBlock.encrypt("plaintext", "key", AESKeyType.AES_128)</code>
 * <code>ElectronicCodeBlock.decrypt("cyphertext", "key", AESKeyType.AES_128)</code>
 *
 * References:
 * https://en.wikipedia.org/wiki/Block_cipher_mode_of_operation
 * https://medium.com/asecuritysite-when-bob-met-alice/electronic-code-book-ecb-and-cipher-block-chaining-cbc-e3309d704917
 */
public class ElectronicCodeBlock {

    private static final int BLOCK_SIZE = 16;
    private static final String NULL_CHAR = "\0";

    /**
     * Encrypt given plaintext with Electronic Code Block Mode of Operation.
     * @param plaintext plaintext
     * @return generated cyphertext from concatenation of each block
     */
    public static String encrypt(String plaintext, String key, AESKeyType aesKeyType) {
        return run(plaintext.getBytes(), key, aesKeyType, ECBMode.ENCRYPT);
    }

    /**
     * Decrypt given cyphertext with Electronic Code Block Mode of Operation.
     * @param cyphertext cyphertext
     * @return generated cyphertext from concatenation of each block
     */
    public static String decrypt(String cyphertext, String key, AESKeyType aesKeyType) {
        return run(HexBin.decode(cyphertext), key, aesKeyType, ECBMode.DECRYPT).replace(NULL_CHAR, "");
    }

    private static String run(byte[] plaintextBytes, String key, AESKeyType aesKeyType, ECBMode ecbMode) {

        // convert strings to bytes
        byte[] keyBytes = key.getBytes();

        // allocate byte blocks
        int blockCount = (int) Math.ceil(plaintextBytes.length / 16.0);
        byte[][] plaintextBlocks = new byte[blockCount][BLOCK_SIZE];

        // split bytes to blocks and copy them into allocated blocks
        for (int i = 0; i < plaintextBlocks.length; i++) {
            plaintextBlocks[i] = Arrays.copyOfRange(plaintextBytes, i * BLOCK_SIZE, (i+1) * BLOCK_SIZE);
        }

        // encrypt block and concatenate in parallel
        return Arrays.stream(plaintextBlocks)
                .parallel()
                .map(block -> ecbMode.isEncrypt()
                        ? HexBin.encode(AES.encrypt(block, keyBytes, aesKeyType))
                        : new String(AES.decrypt(block, keyBytes, aesKeyType)))
                .collect(Collectors.joining());
    }

    private enum ECBMode {
        ENCRYPT, DECRYPT;

        boolean isEncrypt() {
            return ECBMode.ENCRYPT.equals(this);
        }
    }

}
