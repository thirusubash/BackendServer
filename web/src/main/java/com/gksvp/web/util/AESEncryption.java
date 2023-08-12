package com.gksvp.web.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
public class AESEncryption {

    @Value("${encryption.secretkey}")
    private String secretKey;

    @Value("${encryption.iv}")
    private String iv;

    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String AES_KEY_ALGORITHM = "AES";

    public String encrypt(String plaintext) throws Exception {
        SecretKeySpec keySpec = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String ciphertext) throws Exception {
        SecretKeySpec keySpec = generateKey(secretKey);
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        byte[] encryptedBytes = Base64.getDecoder().decode(ciphertext);
        IvParameterSpec ivSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));

        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    private SecretKeySpec generateKey(String secretKey) throws Exception {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes, AES_KEY_ALGORITHM);
    }

}
