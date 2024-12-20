package org.example.DAO;

import org.example.jdbc.DBConnection;
import org.example.model.Signature;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SignatureDAO {

    // Thêm một chữ ký mới
    public int addSignature(String signValue, int orderId) {
        String sql = "INSERT INTO signature (sign_value, order_id) VALUES (?, ?)";
        int generatedSignId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, signValue);
            stmt.setInt(2, orderId);

            stmt.executeUpdate();

            // Lấy ID của chữ ký vừa được tạo
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedSignId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedSignId;
    }

    // Tìm chữ ký theo ID
    public Signature getSignatureById(int signId) {
        String sql = "SELECT * FROM signature WHERE sign_id = ?";
        Signature signature = null;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, signId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                signature = new Signature(
                        rs.getInt("sign_id"),
                        rs.getString("sign_value"),
                        rs.getInt("order_id"),
                        rs.getTimestamp("sign_time")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return signature;
    }

    // Lấy tất cả chữ ký liên quan đến một order_id
    public List<Signature> getSignaturesByOrderId(int orderId) {
        String sql = "SELECT * FROM signature WHERE order_id = ?";
        List<Signature> signatures = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                signatures.add(new Signature(
                        rs.getInt("sign_id"),
                        rs.getString("sign_value"),
                        rs.getInt("order_id"),
                        rs.getTimestamp("sign_time")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return signatures;
    }

    // Xóa chữ ký theo ID
    public boolean deleteSignatureById(int signId) {
        String sql = "DELETE FROM signature WHERE sign_id = ?";
        boolean isDeleted = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, signId);

            isDeleted = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isDeleted;
    }



    public boolean updateSignature(Signature signature) {
        String sql = "UPDATE signature SET sign_value = ?, sign_time = ? WHERE order_id = ?";
        boolean isUpdated = false;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, signature.getSignValue());
            stmt.setTimestamp(2, signature.getSignTime());
            stmt.setInt(3, signature.getOrderId());

            isUpdated = stmt.executeUpdate() > 0; // Nếu cập nhật thành công, trả về true
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return isUpdated;
    }
}
