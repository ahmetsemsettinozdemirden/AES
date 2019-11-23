import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AESTest {

    @Test
    public void it_should_encrypt_given_plaintext_block_and_key_block(){
        // Given
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c");
        byte[] cyphertextBlock = HexBin.decode("3925841D02DC09FBDC118597196A0B32");

        // When
        byte[] resultBlock = AES.encrypt(plaintextBlock, key, AESKeyType.AES_128);

        // Then
        assertArrayEquals(cyphertextBlock, resultBlock);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_block_and_key_block(){
        // Given
        byte[] cyphertextBlock = HexBin.decode("3925841D02DC09FBDC118597196A0B32");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c");
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");

        // When
        byte[] resultBlock = AES.decrypt(cyphertextBlock, key, AESKeyType.AES_128);

        // Then
        assertArrayEquals(plaintextBlock, resultBlock);
    }

    @Test
    public void it_should_encrypt_given_plaintext_block_and_192_bit_key_block(){
        // Given
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c2b7e151628aed2a6");
        byte[] cyphertextBlock = HexBin.decode("4FCB8DB85784A2C1BB77DB7EDE3217AC");

        // When
        byte[] resultBlock = AES.encrypt(plaintextBlock, key, AESKeyType.AES_192);

        // Then
        assertArrayEquals(cyphertextBlock, resultBlock);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_block_and_192_bit_key_block(){
        // Given
        byte[] cyphertextBlock = HexBin.decode("4FCB8DB85784A2C1BB77DB7EDE3217AC");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c2b7e151628aed2a6");
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");

        // When
        byte[] resultBlock = AES.decrypt(cyphertextBlock, key, AESKeyType.AES_192);

        // Then
        assertArrayEquals(plaintextBlock, resultBlock);
    }

    @Test
    public void it_should_encrypt_given_plaintext_block_and_256_bit_key_block(){
        // Given
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c2b7e151628aed2a6abf7158809cf4f3c");
        byte[] cyphertextBlock = HexBin.decode("300B8C50403AEA205E3D8BBB2A0182B5");

        // When
        byte[] resultBlock = AES.encrypt(plaintextBlock, key, AESKeyType.AES_256);

        // Then
        assertArrayEquals(cyphertextBlock, resultBlock);
    }

    @Test
    public void it_should_decrypt_given_cyphertext_block_and_256_bit_key_block(){
        // Given
        byte[] cyphertextBlock = HexBin.decode("300B8C50403AEA205E3D8BBB2A0182B5");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c2b7e151628aed2a6abf7158809cf4f3c");
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");

        // When
        byte[] resultBlock = AES.decrypt(cyphertextBlock, key, AESKeyType.AES_256);

        // Then
        assertArrayEquals(plaintextBlock, resultBlock);
    }

    @Test
    public void it_should_throw_exception_when_text_length_is_not_128_bits(){
        // Given
        byte[] plaintextBlock = HexBin.decode("2b7e");
        byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> AES.encrypt(plaintextBlock, key, AESKeyType.AES_128));
    }

    @Test
    public void it_should_throw_exception_when_key_length_is_not_128_bits(){
        // Given
        byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");
        byte[] key = HexBin.decode("2b7e");

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> AES.encrypt(plaintextBlock, key, AESKeyType.AES_128));
    }

}
