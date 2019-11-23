/**
 * AES Key Generation (Key Extension or Key Schedule)
 *
 * Usage:
 * <code>AESKeyGenerator.generateRoundKeys(key, AESKeyType.AES_128)</code>
 *
 * References:
 * AES Rijndael Cipher Flash animation: https://www.youtube.com/watch?v=gP4PqVGudtg
 * What is AES? - Step by step: https://medium.com/@14wnrkim/what-is-aes-step-by-step-fcb2ba41bb20
 * AES key schedule: https://en.wikipedia.org/wiki/AES_key_schedule
 */
public class AESKeyGenerator {

    private static final int WORD_NUMBER_IN_BLOCK = 4; // (nb)
    private static final int WORD_LENGTH = 4;

    /**
     * AES key extension.
     *
     * @param key original key, round keys generated from this key
     * @param aesKeyType specifies key length: 128 bits, 192 bits, 256 bits
     * @return round keys: original key + round keys; number of round keys specified by AESKeyType
     */
    public static byte[][] generateRoundKeys(byte[] key, AESKeyType aesKeyType) {

        int wordNumberOfInputKey = aesKeyType.getWordNumber();  // column number(nk), 128 -> 4, 192 -> 6, 256 -> 8
        int roundNumber = wordNumberOfInputKey + 6;             // round number(nr), 128 -> 10, 192 -> 12, 256 -> 14

        final int totalKeyNumber = WORD_NUMBER_IN_BLOCK * (roundNumber + 1);
        byte[] expendedKey = new byte[WORD_LENGTH * totalKeyNumber];

        // copy original key
        System.arraycopy(key, 0, expendedKey, 0, key.length);

        for (int i = wordNumberOfInputKey; i < totalKeyNumber; i++) {

            byte[] temp = new byte[WORD_LENGTH];
            System.arraycopy(expendedKey, (i - 1) * WORD_LENGTH, temp, 0, 4);

            if(i % wordNumberOfInputKey == 0) {
                circularShift(temp);
                substituteBytes(temp);
                xorRcon(temp, (i - wordNumberOfInputKey) / wordNumberOfInputKey);
            } else if(wordNumberOfInputKey > 6 && (i % wordNumberOfInputKey) == 4) { // for 256 bit key
                substituteBytes(temp);
            }

            // get previous word
            byte[] prevColumn = new byte[WORD_LENGTH];
            System.arraycopy(expendedKey, (i - wordNumberOfInputKey) * WORD_LENGTH, prevColumn, 0, 4);

            xor(prevColumn, temp);

            // copy computed word to expended key
            System.arraycopy(temp, 0, expendedKey, i * WORD_LENGTH, 4);
        }

        // split into keys (2D array)
        byte[][] roundKeys = new byte[roundNumber + 1][WORD_LENGTH * WORD_NUMBER_IN_BLOCK];
        for (int i = 0; i < roundKeys.length; i++) {
            System.arraycopy(expendedKey, i * WORD_LENGTH * WORD_NUMBER_IN_BLOCK, roundKeys[i], 0, 16);
        }

        return roundKeys;
    }

    /**
     * Shift given word.
     * @param roundColumn word
     */
    private static void circularShift(byte[] roundColumn) {
        byte temp = roundColumn[0];
        for (int i = 0; i < 3; i++) {
            roundColumn[i] = roundColumn[i+1];
        }
        roundColumn[3] = temp;
    }

    /**
     * Substitute word's elements by SBox values.
     * @param roundColumn word
     */
    private static void substituteBytes(byte[] roundColumn) {
        for (int i = 0; i < 4; i++) {
            roundColumn[i] = SBox.getValue(roundColumn[i]);
        }
    }

    /**
     * XOR given word with rcon(i) value.
     * @param roundColumn word
     * @param keyNo key number (i)
     */
    private static void xorRcon(byte[] roundColumn, int keyNo) {
        byte[] rconCol = RCON.getValue(keyNo);
        for (int i = 0; i < 4; i++) {
            roundColumn[i] = (byte) (roundColumn[i] ^ rconCol[i]);
        }
    }

    /**
     * XOR given words.
     * @param prevColumn K(i - 4)
     * @param roundColumn K(i - 1)
     */
    private static void xor(byte[] prevColumn, byte[] roundColumn) {
        for (int i = 0; i < 4; i++) {
            roundColumn[i] = (byte) (prevColumn[i] ^ roundColumn[i]);
        }
    }

}
