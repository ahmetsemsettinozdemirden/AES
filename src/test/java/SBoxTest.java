import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://en.wikipedia.org/wiki/Rijndael_S-box
 */
public class SBoxTest {

    @Test
    public void it_should_get_value_from_sbox() {
        // Given
        byte givenByte = (byte) 0xd1;

        // When
        byte substitutedByte = SBox.getValue(givenByte);

        // Then
        assertEquals((byte) 0x3e, substitutedByte);
    }

    @Test
    public void it_should_get_value_from_inverse_sbox() {
        // Given
        byte givenByte = (byte) 0xd1;

        // When
        byte substitutedByte = SBox.getInverseValue(givenByte);

        // Then
        assertEquals((byte) 0x51, substitutedByte);
    }

}
