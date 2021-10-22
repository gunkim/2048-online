package dev.gunlog;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        //암호화 키(password)
        jasypt.setPassword("password");
        jasypt.setAlgorithm("PBEWithMD5AndDES");


        //암호화
        String encryptedText = jasypt.encrypt("test");
        //복호화
        String plainText = jasypt.decrypt(encryptedText);

        System.out.println("encryptedText:  " + encryptedText);
        System.out.println("plainText:  " + plainText);
    }
}
