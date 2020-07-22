package com.ylzinfo.librarycard.api;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 加密解密工具包
 *
 * @author Winter Lau
 * @date 2011-12-26
 */
public class CryptoUtils {
    static final String API_CRYPTO_IV = "98516D446AF418112933CC64F9A98C05";
    static final String API_CRYPTO_SALT = "1D2F0B381038C6E1";
    static final int API_CRYPTO_KEY_SIZE = 128;
    static final int API_CRYPTO_ITERATION_COUNT = 1;
    public static final String API_CRYPTO_SECRET_KEY = "test";


    private static final String ALGORITHM_AES = "AES/CBC/PKCS5Padding";
    private Cipher cipher;
    private SecretKey secretKey;
    private IvParameterSpec ivParameterSpec;

    public CryptoUtils(String salt, String iv, int iterationCount, int keySize, String passphrase) throws Exception {
        secretKey = generateKey(salt, iterationCount, keySize, passphrase);
        ivParameterSpec = new IvParameterSpec(hex2byte(iv.getBytes()));
        cipher = Cipher.getInstance(ALGORITHM_AES);
    }

    public static CryptoUtils getInstance() {
        try {
            return   new CryptoUtils(API_CRYPTO_SALT, API_CRYPTO_IV, API_CRYPTO_ITERATION_COUNT, API_CRYPTO_KEY_SIZE, API_CRYPTO_SECRET_KEY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null ;
    }

    private SecretKey generateKey(String salt, int iterationCount, int keySize, String passphrase) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec spec = new PBEKeySpec(passphrase.toCharArray(), hex2byte(salt.getBytes()), iterationCount, keySize);
        SecretKey key = new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
        return key;
    }

    public String encrypt(String plaintext) {
        try {
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
            return byte2hex(cipher.doFinal(plaintext.getBytes("UTF-8")));
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String ciphertext) {
        try {
            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            return new String(cipher.doFinal(hex2byte(ciphertext.getBytes("UTF-8"))));
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 二行制转字符串
     *
     * @param b
     * @return
     */
    private static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (int n = 0; b != null && n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0XFF);
            if (stmp.length() == 1)
                hs.append('0');
            hs.append(stmp);
        }
        return hs.toString();
    }

    private static byte[] hex2byte(byte[] b) {
        if ((b.length % 2) != 0)
            throw new IllegalArgumentException();
        byte[] b2 = new byte[b.length / 2];
        for (int n = 0; n < b.length; n += 2) {
            String item = new String(b, n, 2);
            b2[n / 2] = (byte) Integer.parseInt(item, 16);
        }
        return b2;
    }

    public static String pwdEncrypt(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        String salt = "MSYOS";
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update((str + salt).getBytes("UTF-8"));
        byte[] encrypted = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < encrypted.length; i++) {
            if (Integer.toHexString(0xff & encrypted[i]).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xff & encrypted[i]));
            } else {
                sb.append(Integer.toHexString(0xff & encrypted[i]));
            }
        }
        return sb.toString();
    }

    public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        md5.update(str.getBytes("UTF-8"));
        byte[] encrypted = md5.digest();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < encrypted.length; i++) {
            if (Integer.toHexString(0xff & encrypted[i]).length() == 1) {
                sb.append("0").append(Integer.toHexString(0xff & encrypted[i]));
            } else {
                sb.append(Integer.toHexString(0xff & encrypted[i]));
            }
        }
        return sb.toString();
    }

}