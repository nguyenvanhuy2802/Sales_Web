package org.example.DAO;

// PaymentDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class PaymentDAO {

    // Thêm thanh toán mới
    public void addPayment(Payment payment) {
        String sql = "INSERT INTO payments (order_id, amount, payment_method, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setBigDecimal(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getStatus());

            stmt.executeUpdate();

            // Lấy payment_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                payment.setPaymentId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy thanh toán theo ID
    public Payment getPaymentById(int paymentId) {
        String sql = "SELECT * FROM payments WHERE payment_id = ?";
        Payment payment = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setStatus(rs.getString("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return payment;
    }

    // Lấy tất cả thanh toán
    public List<Payment> getAllPayments() {
        String sql = "SELECT * FROM payments";
        List<Payment> payments = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Payment payment = new Payment();
                payment.setPaymentId(rs.getInt("payment_id"));
                payment.setOrderId(rs.getInt("order_id"));
                payment.setPaymentDate(rs.getTimestamp("payment_date"));
                payment.setAmount(rs.getBigDecimal("amount"));
                payment.setPaymentMethod(rs.getString("payment_method"));
                payment.setStatus(rs.getString("status"));
                payments.add(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return payments;
    }

    // Cập nhật thông tin thanh toán
    public void updatePayment(Payment payment) {
        String sql = "UPDATE payments SET order_id = ?, amount = ?, payment_method = ?, status = ? WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, payment.getOrderId());
            stmt.setBigDecimal(2, payment.getAmount());
            stmt.setString(3, payment.getPaymentMethod());
            stmt.setString(4, payment.getStatus());
            stmt.setInt(5, payment.getPaymentId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa thanh toán
    public void deletePayment(int paymentId)  {
        String sql = "DELETE FROM payments WHERE payment_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, paymentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
