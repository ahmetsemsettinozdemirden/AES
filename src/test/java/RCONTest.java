import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RCONTest {

    @Test
    public void it_should_get_first_rcon_value() {
        // Given
        int i = 0;

        // When
        byte[] result = RCON.getValue(i);

        // Then
        assertArrayEquals(new byte[]{0x01, 0x00, 0x00, 0x00}, result);
    }

    @Test
    public void it_should_get_last_rcon_value() {
        // Given
        int i = 9;

        // When
        byte[] result = RCON.getValue(i);

        // Then
        assertArrayEquals(new byte[]{0x36, 0x00, 0x00, 0x00}, result);
    }

    @Test
    public void it_should_throw_exception_when_i_is_lower_then_0() {
        // Given
        int i = -1;

        // When & Then
        assertThrows(IllegalArgumentException.class, () -> RCON.getValue(i));
    }


}
