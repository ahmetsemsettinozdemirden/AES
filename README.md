# AES
This project is simple implementation of Advanced Encryption Standard (AES). The purpose here is learning and documenting AES. Project is unit tested and it has 100% test coverage. 128bit, 192bit or 256bit keys are supported as described in AES. Also for string Electronic Code Book method is implemented.

# Usage
You can either use Electronic Code Book (ECB) method to encrypt/decrypt strings or directly use AES with 128bit block.

### ECB
```java
String plaintext = "Lorem ipsum dolor sit amet...";
String key = "one two nine two";

String cypherText = ElectronicCodeBlock.encrypt(plaintext, key, AESKeyType.AES_128);
```

### Block
```java
byte[] plaintextBlock = HexBin.decode("3243f6a8885a308d313198a2e0370734");
byte[] key = HexBin.decode("2b7e151628aed2a6abf7158809cf4f3c");

byte[] cyphertextBlock = AES.encrypt(plaintextBlock, key, AESKeyType.AES_128);
```

#Author
A. Semsettin Ozdemirden - [Github](https://github.com/ahmetsemsettinozdemirden)
