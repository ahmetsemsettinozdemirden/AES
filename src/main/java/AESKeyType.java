/**
 * AES key types that specifies key length, word number and round number of that key.
 */
public enum AESKeyType {
    AES_128(16, 4, 10),
    AES_192(24, 6, 12),
    AES_256(32, 8, 14);

    private int keyLength;
    private int wordNumber;
    private int roundNumber;

    AESKeyType(int keyLength, int wordNumber, int roundNumber) {
        this.keyLength = keyLength;
        this.wordNumber = wordNumber;
        this.roundNumber = roundNumber;
    }

    public int getKeyLength() {
        return keyLength;
    }

    public int getWordNumber() {
        return wordNumber;
    }

    public int getRoundNumber() {
        return roundNumber;
    }
}
