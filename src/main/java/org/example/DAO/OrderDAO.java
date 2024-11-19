package org.example.DAO;

// OrderDAO.java

import org.example.jdbc.DBConnection;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class OrderDAO {
    // OrderDAO.java
    public int addOrderId(Order order) {
        String sql = "INSERT INTO orders (customer_id, total_amount) VALUES (?, ?)";
        int generatedOrderId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setBigDecimal(2, order.getTotalAmount());

            stmt.executeUpdate();

            // Retrieve generated order ID
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedOrderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedOrderId;
    }

    // Thêm đơn hàng mới
    public void addOrder(Order order) {
        String sql = "INSERT INTO orders (customer_id, status, total_amount) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getStatus());
            stmt.setBigDecimal(3, order.getTotalAmount());

            stmt.executeUpdate();

            // Lấy order_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                order.setOrderId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy đơn hàng theo ID
    public Order getOrderById(int orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        Order order = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    // Lấy tất cả đơn hàng
    public List<Order> getAllOrders() {
        String sql = "SELECT * FROM orders";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    // Cập nhật thông tin đơn hàng
    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_id = ?, status = ?, total_amount = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getStatus());
            stmt.setBigDecimal(3, order.getTotalAmount());
            stmt.setInt(4, order.getOrderId());

            // Execute the update and check if any rows were affected
            int rowsAffected = stmt.executeUpdate();

            // If at least one row was affected, return true (update successful)
            return rowsAffected > 0;
        } catch (SQLException e) {
            // Handle exceptions and return false in case of errors
            e.printStackTrace(); // Log the exception (optional)
            return false;
        }
    }

    // Xóa đơn hàng
    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM orders WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
