package com.example.reactboot.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.KeySpec;

public class AES128Crypto {
    public AES128Crypto() {
    }

    public static String decrypt(String cipherText) {
        try {
            return decrypt("af85f006367998b6b2bca58a6030e311", "12716ba6a86a35eded7a4076baa6e4fe", "4d6e5da106d63f73ca5d93bcc5ac5434", cipherText, 10000, 128);
        } catch (Exception var2) {
            return "";
        }
    }

    private static String decrypt(String salt, String iv, String passphrase, String cipherText, int iterationCount, int keySize) throws Exception {
        SecretKeyFactory factory    = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec                = new PBEKeySpec(passphrase.toCharArray(), Hex.decodeHex(salt.toCharArray()), iterationCount, keySize);
        SecretKey key               = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher               = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(2, key, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
        byte[] decrypted            = cipher.doFinal(Base64.decodeBase64(cipherText));

        return new String(decrypted, "UTF-8");
    }

    public static String encrypt(String plainText) {
        try {
            return encrypt("af85f006367998b6b2bca58a6030e311", "12716ba6a86a35eded7a4076baa6e4fe", "4d6e5da106d63f73ca5d93bcc5ac5434", plainText, 10000, 128);
        } catch (Exception var2) {
            return "";
        }
    }

    private static String encrypt(String salt, String iv, String passphrase, String plainText, int iterationCount, int keySize) throws Exception {
        SecretKeyFactory factory    = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec                = new PBEKeySpec(passphrase.toCharArray(), Hex.decodeHex(salt.toCharArray()), iterationCount, keySize);
        SecretKey key               = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        Cipher cipher               = Cipher.getInstance("AES/CBC/PKCS5Padding");

        cipher.init(1, key, new IvParameterSpec(Hex.decodeHex(iv.toCharArray())));
        byte[] encrypted            = cipher.doFinal(plainText.getBytes("UTF-8"));

        return new String(Base64.encodeBase64(encrypted));
    }

    static class $ {
        static final String passPhrase  = "4d6e5da106d63f73ca5d93bcc5ac5434";
        static final String salt        = "af85f006367998b6b2bca58a6030e311";
        static final String iv          = "12716ba6a86a35eded7a4076baa6e4fe";
        static final int keySize        = 128;
        static final int iterationCount = 10000;

        $() {
        }
    }
}
