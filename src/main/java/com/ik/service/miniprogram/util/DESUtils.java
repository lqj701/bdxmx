package com.ik.service.miniprogram.util;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DESUtils {
    private static Logger logger = LoggerFactory.getLogger(DESUtils.class);

    public static final String ALGORITHM = "DES";

    public static byte[] encrypt(byte[] data, byte[] key) {
        byte[] encryptedData = null;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESUtils.ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance(DESUtils.ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

            encryptedData = cipher.doFinal(data);
        } catch (Exception e) {
            DESUtils.logger.error("Catch a exception: {}", e.getMessage(), e);
        }

        return encryptedData;
    }

    public static String encrypt(String data, String password) {
        if (data == null || password == null) {
            return null;
        }

        byte[] encryptedBytes = DESUtils.encrypt(data.getBytes(), password.getBytes());

        return DESUtils.byte2hex(encryptedBytes);
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        byte[] decryptedData = null;

        try {
            SecureRandom sr = new SecureRandom();
            DESKeySpec dks = new DESKeySpec(key);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DESUtils.ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(dks);

            Cipher cipher = Cipher.getInstance(DESUtils.ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

            decryptedData = cipher.doFinal(data);
        } catch (Exception e) {
            DESUtils.logger.error("Catch a exception: {}", e.getMessage(), e);
        }

        return decryptedData;
    }

    public static String decrypt(String encryptedData, String password) {
        if (encryptedData == null || password == null) {
            return null;
        }

        byte[] decryptedBytes = DESUtils.decrypt(DESUtils.hex2byte(encryptedData), password.getBytes());

        return new String(decryptedBytes);
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();

        if (bytes == null || bytes.length <= 0) {
            return null;
        }

        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            String hex = Integer.toHexString(v);

            if (hex.length() < 2) {
                sb.append('0');
            }

            sb.append(hex);
        }

        return sb.toString();
    }

    public static byte[] hex2byte(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }

        if ((hexString.length() % 2) != 0) {
            throw new IllegalArgumentException("长度不是偶数");
        }

        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] bytes = new byte[length];

        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            bytes[i] = (byte) (DESUtils.charToByte(hexChars[pos]) << 4 | DESUtils.charToByte(hexChars[pos + 1]));
        }

        return bytes;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789abcdef".indexOf(c);
    }
}
