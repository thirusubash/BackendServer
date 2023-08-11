package com.gksvp.web.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AESEncryption {
    @Value("${encryption.secretKey}")
    private String secretKey;

    @Value("${encryption.iv}")
    private String iv;

    private static final String AES_ALGORITHM = "AES/CBC/PKCS5Padding";
    private static final String AES_KEY_ALGORITHM = "AES";

    private IvParameterSpec ivParameterSpec;

    public AESEncryption() {
        this.ivParameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
    }

    public String encrypt(String plaintext) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE,
                new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_KEY_ALGORITHM), ivParameterSpec);
        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String ciphertext) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE,
                new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), AES_KEY_ALGORITHM), ivParameterSpec);
        byte[] decodedCiphertext = Base64.getDecoder().decode(ciphertext);
        byte[] decryptedBytes = cipher.doFinal(decodedCiphertext);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }
}
