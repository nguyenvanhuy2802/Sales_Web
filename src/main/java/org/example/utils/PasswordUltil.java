package org.example.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordUltil {
    public static String hashPassword(String plainPassword) {
        // Tạo salt và hash mật khẩu
        String hashedPassword = BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        return hashedPassword;
    }
    public static boolean checkPassword(String enteredPassword, String storedHashedPassword) {
        // So sánh mật khẩu người dùng nhập với hash lưu trữ trong DB
        return BCrypt.checkpw(enteredPassword, storedHashedPassword);
    }

    public static void main(String[] args) {
        System.out.println(hashPassword("1"));
    }
}
