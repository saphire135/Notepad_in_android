package com.fareye.divyanshu.notepad.encryptDecrypt;

/**
 * Created by diyanshu on 9/8/17.
 */

import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class EncryptDecrypt {

    public String encrypt(String text) {
        try {

            String key = "Bar12345Bar12345"; // 128 bit key

            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(text.getBytes());
            return (new String(encrypted));

            // decrypt the text

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String text) {
        try {

            String key = "Bar12345Bar12345"; // 128 bit key

            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");

            // encrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            String decrypted = new String(cipher.doFinal(text.getBytes()));

            return decrypted;
            // decrypt the text

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
