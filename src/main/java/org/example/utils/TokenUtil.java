package org.example.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TokenUtil {
    private static final long EXPIRATION_TIME = 5 * 60 * 1000; // 5 phút
    private static final Map<String, Long> tokenStore = new HashMap<>();

    public static String generateToken(String email) {
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, System.currentTimeMillis());
        return token;
    }

    public static boolean isValidToken(String token) {
        Long createdTime = tokenStore.get(token);
        if (createdTime == null) {
            return false;
        }

        // Kiểm tra thời gian hết hạn
        if (System.currentTimeMillis() - createdTime > EXPIRATION_TIME) {
            tokenStore.remove(token);
            return false;
        }

        return true;
    }

    public static String getEmailFromToken(String token) {
        // Trong trường hợp cần thêm email vào token, lưu lại email trong `tokenStore`
        return null; // Cập nhật theo yêu cầu
    }
}
