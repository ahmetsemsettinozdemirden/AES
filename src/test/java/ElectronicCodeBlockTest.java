import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ElectronicCodeBlockTest {

    @Test
    public void it_should_encrypt_given_plaintext_and_key() {
        // Given
        String plaintext = "0123456789012345"; // 16 bytes
        String key = "one two nine two";
        String cyphertext = "B77272F76A6A09C662196EDCF5863500";

        // When
        String resultText = ElectronicCodeBlock.encrypt(plaintext, key, AESKeyType.AES_128);

        // Then
        assertEquals(cyphertext, resultText);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_and_key() {
        // Given
        String cyphertext = "B77272F76A6A09C662196EDCF5863500";  // 1 block
        String key = "one two nine two";
        String plaintext = "0123456789012345";

        // When
        String resultText = ElectronicCodeBlock.decrypt(cyphertext, key, AESKeyType.AES_128);

        // Then
        assertEquals(plaintext, resultText);
    }

    @Test
    public void it_should_encrypt_given_plaintext_and_key_with_padding() {
        // Given
        String plaintext = "This Plaintext Requires Padding..."; // 34 bytes
        String key = "one two nine two";
        String cyphertext = "D6863711A2DFF9D076A282A2E01F728EA1B2192B05F380306697C051D18E3FE95F59F7F5DC2DF82A5B058E6E1C116A67";

        // When
        String resultText = ElectronicCodeBlock.encrypt(plaintext, key, AESKeyType.AES_128);

        // Then
        assertEquals(cyphertext, resultText);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_and_key_with_padding() {
        // Given
        String cyphertext = "D6863711A2DFF9D076A282A2E01F728EA1B2192B05F380306697C051D18E3FE95F59F7F5DC2DF82A5B058E6E1C116A67"; // 3 blocks
        String key = "one two nine two";
        String plaintext = "This Plaintext Requires Padding...";

        // When
        String resultText = ElectronicCodeBlock.decrypt(cyphertext, key, AESKeyType.AES_128);

        // Then
        assertEquals(plaintext, resultText);
    }

    @Test
    public void it_should_encrypt_given_plaintext_and_256_bit_key() {
        // Given
        String plaintext = "0123456789012345"; // 16 bytes
        String key = "one two nine twoone two nine two"; // 256bit
        String cyphertext = "857B628F557C452397669557BADAFB5E";

        // When
        String resultText = ElectronicCodeBlock.encrypt(plaintext, key, AESKeyType.AES_256);

        // Then
        assertEquals(cyphertext, resultText);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_and_256_bit_key() {
        // Given
        String cyphertext = "857B628F557C452397669557BADAFB5E";
        String key = "one two nine twoone two nine two"; // 256bit
        String plaintext = "0123456789012345"; // 16 bytes

        // When
        String resultText = ElectronicCodeBlock.decrypt(cyphertext, key, AESKeyType.AES_256);

        // Then
        assertEquals(plaintext, resultText);
    }

}
