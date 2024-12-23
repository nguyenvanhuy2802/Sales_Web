package org.example.utils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;

public class RSAUtil {
    private static final String ALGORITHM = "RSA";
    private static KeyPair keyPair = generateKeyPair();

    // Phương thức sinh cặp khóa RSA
    public static KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi sinh cặp khóa RSA", e);
        }
    }

    // Lấy khóa công khai từ cặp khóa
    public static PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    // Lấy khóa riêng từ cặp khóa
    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    // Phương thức mã hóa chuỗi bằng khóa công khai
    public static String encrypt(String data, PublicKey publicKey) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] encryptedBytes = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedBytes); // Chuyển byte mã hóa sang Base64 để lưu trữ
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi mã hóa RSA", e);
        }
    }

    // Phương thức giải mã chuỗi bằng khóa riêng
    public static String decrypt(String encryptedData, PrivateKey privateKey) {
        try {
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); // Chỉ định chế độ padding
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            // Decode Base64 input
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);

            // Giải mã và trả về chuỗi
            return new String(cipher.doFinal(decodedBytes), StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Dữ liệu mã hóa không hợp lệ hoặc bị thay đổi", e);
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi giải mã RSA", e);
        }
    }
}
