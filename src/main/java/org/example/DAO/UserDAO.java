package org.example.DAO;

import org.example.jdbc.DBConnection;
import org.example.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // Thêm người dùng mới
    public void addUser(User user)  {
        String sql = "INSERT INTO users (name, email, phone, username, address, password, role, profile_image) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getUsername()); // Added username
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getRole());
            stmt.setString(8, user.getProfileImage());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy người dùng theo ID
    public User getUserById(int userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUsername(rs.getString("username")); // Added username
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setProfileImage(rs.getString("profile_image"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    // Lấy tất cả người dùng
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> users = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setUsername(rs.getString("username")); // Added username
                user.setAddress(rs.getString("address"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                user.setProfileImage(rs.getString("profile_image"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    // Cập nhật thông tin người dùng
    public void updateUser(User user)  {
        String sql = "UPDATE users SET name = ?, email = ?, phone = ?, username = ?, address = ?, password = ?, role = ?, profile_image = ? WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, user.getName());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getPhone());
            stmt.setString(4, user.getUsername()); // Added username
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getPassword());
            stmt.setString(7, user.getRole());
            stmt.setString(8, user.getProfileImage());
            stmt.setInt(9, user.getUserId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa người dùng
    public void deleteUser(int userId) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
