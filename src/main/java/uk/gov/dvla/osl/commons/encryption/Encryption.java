package uk.gov.dvla.osl.commons.encryption;


public interface Encryption {

    public byte[] encrypt(byte[] data) throws Exception;

    public byte[] decrypt(byte[] data) throws  Exception;
}
