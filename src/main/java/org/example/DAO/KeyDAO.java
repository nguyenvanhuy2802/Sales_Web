package org.example.DAO;

import org.example.jdbc.DBConnection;
import org.example.model.PublicKey;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KeyDAO {
    public boolean addKey(PublicKey publicKey) {
        String sql = "INSERT INTO public_key (user_id, name) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, publicKey.getUserId());
            stmt.setString(2, publicKey.getName());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PublicKey> getPublicKeyByUserIdAndStatus(int userId, String status) {
        String sql = "SELECT * FROM public_key WHERE user_id = ? AND status = ?";
        List<PublicKey> publicKeys = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, status);

            ResultSet rs = stmt.executeQuery();

            // Loop through the results
            while (rs.next()) {
                PublicKey publicKey = new PublicKey();
                publicKey.setKeyId(rs.getInt("key_id"));
                publicKey.setUserId(rs.getInt("user_id"));
                publicKey.setName(rs.getString("name"));
                publicKey.setCreateTime(rs.getTimestamp("createdTime"));
                publicKey.setEndTime(rs.getTimestamp("endTime"));
                publicKey.setStatus(rs.getString("status"));
                publicKey.setReportTime(rs.getTimestamp("reportTime"));
                publicKey.setLeakTime(rs.getTimestamp("leakTime"));  // Set leakTime
                publicKeys.add(publicKey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publicKeys;
    }

    public PublicKey getPublicKeyByKeyId(int keyId) {
        String sql = "SELECT * FROM public_key WHERE key_id = ?";
        PublicKey publicKey = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, keyId);

            ResultSet rs = stmt.executeQuery();

            // Nếu tìm thấy bản ghi
            if (rs.next()) {
                publicKey = new PublicKey();
                publicKey.setKeyId(rs.getInt("key_id"));
                publicKey.setUserId(rs.getInt("user_id"));
                publicKey.setName(rs.getString("name"));
                publicKey.setCreateTime(rs.getTimestamp("createdTime"));
                publicKey.setEndTime(rs.getTimestamp("endTime"));
                publicKey.setStatus(rs.getString("status"));
                publicKey.setReportTime(rs.getTimestamp("reportTime"));
                publicKey.setLeakTime(rs.getTimestamp("leakTime")); // Set leakTime
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publicKey;
    }



    public boolean updateKey(PublicKey publicKey, String status) {
        String sql = "UPDATE public_key SET status = ?, reportTime = ?, leakTime = ? WHERE user_id = ? AND status = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Set values for the parameters
            stmt.setString(1, status);
            stmt.setTimestamp(2, publicKey.getReportTime());
            stmt.setTimestamp(3, publicKey.getLeakTime());  // Set leakTime
            stmt.setInt(4, publicKey.getUserId());
            stmt.setString(5, publicKey.getStatus());

            // Execute the update
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row is updated
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<PublicKey> getAllKeys() {
        String sql = "SELECT * FROM public_key";
        List<PublicKey> publicKeys = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            // Lặp qua các kết quả
            while (rs.next()) {
                PublicKey publicKey = new PublicKey();
                publicKey.setKeyId(rs.getInt("key_id"));
                publicKey.setUserId(rs.getInt("user_id"));
                publicKey.setName(rs.getString("name"));
                publicKey.setCreateTime(rs.getTimestamp("createdTime"));
                publicKey.setEndTime(rs.getTimestamp("endTime"));
                publicKey.setStatus(rs.getString("status"));
                publicKey.setReportTime(rs.getTimestamp("reportTime"));
                publicKey.setLeakTime(rs.getTimestamp("leakTime")); // Lấy leakTime nếu có
                publicKeys.add(publicKey);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return publicKeys;
    }


}
