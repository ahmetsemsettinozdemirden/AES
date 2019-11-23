
/**
 * AES (Advanced Encryption Standard) (Rijndael cipher)
 *
 * Usage:
 * <code>AES.encrypt(plaintext, key, AESKeyType.AES_128)</code>
 * <code>AES.decrypt(cyphertext, key, AESKeyType.AES_128)</code>
 *
 * References:
 * AES Rijndael Cipher Flash animation: https://www.youtube.com/watch?v=gP4PqVGudtg
 * What is AES? - Step by step: https://medium.com/@14wnrkim/what-is-aes-step-by-step-fcb2ba41bb20
 * https://en.wikipedia.org/wiki/Advanced_Encryption_Standard
 * https://en.wikipedia.org/wiki/Rijndael_S-box
 * https://en.wikipedia.org/wiki/Rijndael_MixColumns
 */
public class AES {

    private static final int BLOCK_SIZE = 16;

    /**
     * AES block encryption
     *
     * @param plaintext bytes to encrypt
     * @param key key bytes in given key specifications
     * @param aesKeyType specify key properties
     * @return
     */
    public static byte[] encrypt(byte[] plaintext, byte[] key, AESKeyType aesKeyType) {
        checkParameters(plaintext, key, aesKeyType);

        byte[][] roundKeys = AESKeyGenerator.generateRoundKeys(key, aesKeyType);
        byte[] state = plaintext.clone();

        // initial round
        addRoundKey(state, roundKeys[0]);

        // main rounds
        for (int round = 1; round < aesKeyType.getRoundNumber(); round++) {
            substituteBytes(state);
            shiftRows(state);
            mixColumns(state);
            addRoundKey(state, roundKeys[round]);
        }

        // final round
        substituteBytes(state);
        shiftRows(state);
        addRoundKey(state, roundKeys[aesKeyType.getRoundNumber()]);

        AESUtil.printHex(state);
        return state;
    }

    /**
     * AES block decryption
     *
     * @param cyphertext bytes to decrypt
     * @param key key bytes in given key specifications
     * @param aesKeyType specify key properties
     * @return
     */
    public static byte[] decrypt(byte[] cyphertext, byte[] key, AESKeyType aesKeyType) {
        checkParameters(cyphertext, key, aesKeyType);

        byte[][] roundKeys = AESKeyGenerator.generateRoundKeys(key, aesKeyType);
        byte[] state = cyphertext.clone();

        // initial round
        addRoundKey(state, roundKeys[aesKeyType.getRoundNumber()]);

        // main rounds
        for (int round = aesKeyType.getRoundNumber(); round > 1; round--) {
            invShiftRows(state);
            invSubstituteBytes(state);
            addRoundKey(state, roundKeys[round - 1]);
            invMixColumns(state);
        }

        // final round
        invShiftRows(state);
        invSubstituteBytes(state);
        addRoundKey(state, roundKeys[0]);

        return state;
    }

    /**
     * Check if text and key is given properly.
     *
     * @param text bytes to encrypt/decrypt
     * @param key key bytes in given key specifications
     * @param aesKeyType specify key properties
     */
    private static void checkParameters(byte[] text, byte[] key, AESKeyType aesKeyType) {
        if (text.length != BLOCK_SIZE)
            throw new IllegalArgumentException("plaintext length must be 128 bits!");
        if (key.length != aesKeyType.getKeyLength())
            throw new IllegalArgumentException(String.format("key length must be %d bits!", (aesKeyType.getKeyLength() * 8)));
    }

    /**
     * XOR state and key.
     *
     * @param state current state of encryption/decryption
     * @param key block key
     */
    private static void addRoundKey(byte[] state, byte[] key) {
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state[i] = (byte) (state[i] ^ key[i]);
        }
    }

    /**
     * Substitute state with SBox values.
     *
     * @param state current state of encryption
     */
    private static void substituteBytes(byte[] state) {
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state[i] = SBox.getValue(state[i]);
        }
    }

    /**
     * Inverse substitute state with InvSBox values.
     *
     * @param state current state of decryption
     */
    private static void invSubstituteBytes(byte[] state) {
        for (int i = 0; i < BLOCK_SIZE; i++) {
            state[i] = SBox.getInverseValue(state[i]);
        }
    }

    /**
     * Each word is shifted left by;
     * first word: 0,
     * second word: 1,
     * third word 2,
     * forth word 3,
     *
     * @param state current state of encryption
     */
    private static void shiftRows(byte[] state) {
        // first row is identical
        // second row
        byte t = state[1];
        state[1] = state[5];
        state[5] = state[9];
        state[9] = state[13];
        state[13] = t;

        // third row
        byte t1 = state[2];
        byte t2 = state[6];
        state[2] = state[10];
        state[6] = state[14];
        state[10] = t1;
        state[14] = t2;

        // forth row
        t = state[3];
        state[3] = state[15];
        state[15] = state[11];
        state[11] = state[7];
        state[7] = t;
    }

    /**
     * Each word is shifted right by;
     * first word: 0,
     * second word: 1,
     * third word 2,
     * forth word 3,
     *
     * @param state current state of decryption
     */
    private static void invShiftRows(byte[] state) {
        // first row is identical
        // second row
        byte t = state[13];
        state[13] = state[9];
        state[9] = state[5];
        state[5] = state[1];
        state[1] = t;

        // third row
        byte t1 = state[10];
        byte t2 = state[14];
        state[10] = state[2];
        state[14] = state[6];
        state[2] = t1;
        state[6] = t2;

        // forth row
        t = state[7];
        state[7] = state[11];
        state[11] = state[15];
        state[15] = state[3];
        state[3] = t;
    }

    /**
     * Multiply each word with MDS matrix.
     *
     * @param state current state of encryption
     */
    private static void mixColumns(byte[] state) {
        mixColumns(state, MDS.getMDS());
    }

    /**
     * Multiply each word with InvMDS matrix.
     *
     * @param state current state of decryption
     */
    private static void invMixColumns(byte[] state) {
        mixColumns(state, MDS.getInvMDS());
    }

    private static void mixColumns(byte[] state, byte[][] MDS) {
        byte[] newState = new byte[state.length];
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                // 1 row-column matrix multiplication
                newState[(col * 4) + row] = (byte) (AESUtil.GMul(MDS[row][0], state[(col * 4)]) ^
                                                    AESUtil.GMul(MDS[row][1], state[(col * 4) + 1]) ^
                                                    AESUtil.GMul(MDS[row][2], state[(col * 4) + 2]) ^
                                                    AESUtil.GMul(MDS[row][3], state[(col * 4) + 3]));
            }
        }
        System.arraycopy(newState, 0, state, 0, state.length);
    }

}
