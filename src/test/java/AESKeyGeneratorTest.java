import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * https://www.cryptool.org/en/cto-highlights/aes
 *
 * apache's hex parser used for testing purposes
 */
public class AESKeyGeneratorTest {

    @Test
    public void it_should_generate_10_keys_for_16byte() {
        // Given
        AESKeyType aesKeyType = AESKeyType.AES_128;
        byte[] key = HexBin.decode("000102030405060708090a0b0c0d0e0f");
        byte[][] roundKeys = {
                HexBin.decode("000102030405060708090a0b0c0d0e0f"),
                HexBin.decode("d6aa74fdd2af72fadaa678f1d6ab76fe"),
                HexBin.decode("b692cf0b643dbdf1be9bc5006830b3fe"),
                HexBin.decode("b6ff744ed2c2c9bf6c590cbf0469bf41"),
                HexBin.decode("47f7f7bc95353e03f96c32bcfd058dfd"),
                HexBin.decode("3caaa3e8a99f9deb50f3af57adf622aa"),
                HexBin.decode("5e390f7df7a69296a7553dc10aa31f6b"),
                HexBin.decode("14f9701ae35fe28c440adf4d4ea9c026"),
                HexBin.decode("47438735a41c65b9e016baf4aebf7ad2"),
                HexBin.decode("549932d1f08557681093ed9cbe2c974e"),
                HexBin.decode( "13111d7fe3944a17f307a78b4d2b30c5")
        };

        // When
        byte[][] resultKeys = AESKeyGenerator.generateRoundKeys(key, aesKeyType);

        // Then
        assertEquals(11, resultKeys.length);
        for (int i = 0; i < 11; i++)
            assertArrayEquals(roundKeys, resultKeys);
    }

    @Test
    public void it_should_generate_12_keys_for_24byte() {
        // Given
        AESKeyType aesKeyType = AESKeyType.AES_192;
        byte[] key = HexBin.decode("000102030405060708090a0b0c0d0e0f1011121314151617");
        byte[][] roundKeys = {
                HexBin.decode("000102030405060708090a0b0c0d0e0f"),
                HexBin.decode("10111213141516175846f2f95c43f4fe"),
                HexBin.decode("544afef55847f0fa4856e2e95c43f4fe"),
                HexBin.decode("40f949b31cbabd4d48f043b810b7b342"),
                HexBin.decode("58e151ab04a2a5557effb5416245080c"),
                HexBin.decode("2ab54bb43a02f8f662e3a95d66410c08"),
                HexBin.decode("f501857297448d7ebdf1c6ca87f33e3c"),
                HexBin.decode("e510976183519b6934157c9ea351f1e0"),
                HexBin.decode("1ea0372a995309167c439e77ff12051e"),
                HexBin.decode("dd7e0e887e2fff68608fc842f9dcc154"),
                HexBin.decode("859f5f237a8d5a3dc0c02952beefd63a"),
                HexBin.decode("de601e7827bcdf2ca223800fd8aeda32"),
                HexBin.decode("a4970a331a78dc09c418c271e3a41d5d")
        };
        // When
        byte[][] resultKeys = AESKeyGenerator.generateRoundKeys(key, aesKeyType);

        // Then
        assertEquals(13, resultKeys.length);
        assertArrayEquals(roundKeys[0], resultKeys[0]);
        assertArrayEquals(roundKeys[1], resultKeys[1]);
        assertArrayEquals(roundKeys[2], resultKeys[2]);
        assertArrayEquals(roundKeys[3], resultKeys[3]);
        for (int i = 0; i < 13; i++)
            assertArrayEquals(roundKeys, resultKeys);
    }

    @Test
    public void it_should_generate_14_keys_for_32byte() {
        // Given
        AESKeyType aesKeyType = AESKeyType.AES_256;
        byte[] key = HexBin.decode("000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f");
        byte[][] roundKeys = {
                HexBin.decode("000102030405060708090a0b0c0d0e0f"),
                HexBin.decode("101112131415161718191a1b1c1d1e1f"),
                HexBin.decode("a573c29fa176c498a97fce93a572c09c"),
                HexBin.decode("1651a8cd0244beda1a5da4c10640bade"),
                HexBin.decode("ae87dff00ff11b68a68ed5fb03fc1567"),
                HexBin.decode("6de1f1486fa54f9275f8eb5373b8518d"),
                HexBin.decode("c656827fc9a799176f294cec6cd5598b"),
                HexBin.decode("3de23a75524775e727bf9eb45407cf39"),
                HexBin.decode("0bdc905fc27b0948ad5245a4c1871c2f"),
                HexBin.decode("45f5a66017b2d387300d4d33640a820a"),
                HexBin.decode("7ccff71cbeb4fe5413e6bbf0d261a7df"),
                HexBin.decode("f01afafee7a82979d7a5644ab3afe640"),
                HexBin.decode("2541fe719bf500258813bbd55a721c0a"),
                HexBin.decode("4e5a6699a9f24fe07e572baacdf8cdea"),
                HexBin.decode("24fc79ccbf0979e9371ac23c6d68de36")
        };

        // When
        byte[][] resultKeys = AESKeyGenerator.generateRoundKeys(key, aesKeyType);

        // Then
        assertEquals(15, resultKeys.length);
        for (int i = 0; i < 15; i++)
            assertArrayEquals(roundKeys, resultKeys);
    }

}
