package uk.gov.dvla.osl.commons.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class EncryptionUtil implements Encryption {


    public EncryptionUtil() throws Exception {

    }

    public byte[] encrypt(byte[] data) throws Exception {
        // TODO: Implement your own encryption here
        return data;
    }

    public byte[] decrypt(byte[] encryptedData) throws Exception {
        // TODO: Implement your own decryption here
        return encryptedData;
    }
}
