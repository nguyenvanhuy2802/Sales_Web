package org.example.DAO;

// ShippingDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.Shipping;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShippingDAO {

    // Thêm thông tin giao hàng mới
    public void addShipping(Shipping shipping) throws SQLException {
        String sql = "INSERT INTO shipping (order_id, shipping_address, status) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, shipping.getOrderId());
            stmt.setString(2, shipping.getShippingAddress());
            stmt.setString(3, shipping.getStatus());

            stmt.executeUpdate();

            // Lấy shipping_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                shipping.setShippingId(rs.getInt(1));
            }
        }
    }

    // Lấy thông tin giao hàng theo ID
    public Shipping getShippingById(int shippingId) throws SQLException {
        String sql = "SELECT * FROM shipping WHERE shipping_id = ?";
        Shipping shipping = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shippingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                shipping = new Shipping();
                shipping.setShippingId(rs.getInt("shipping_id"));
                shipping.setOrderId(rs.getInt("order_id"));
                shipping.setShippingAddress(rs.getString("shipping_address"));
                shipping.setShippingDate(rs.getTimestamp("shipping_date"));
                shipping.setDeliveryDate(rs.getTimestamp("delivery_date"));
                shipping.setStatus(rs.getString("status"));
            }
        }
        return shipping;
    }

    // Lấy tất cả thông tin giao hàng
    public List<Shipping> getAllShippings() throws SQLException {
        String sql = "SELECT * FROM shipping";
        List<Shipping> shippings = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Shipping shipping = new Shipping();
                shipping.setShippingId(rs.getInt("shipping_id"));
                shipping.setOrderId(rs.getInt("order_id"));
                shipping.setShippingAddress(rs.getString("shipping_address"));
                shipping.setShippingDate(rs.getTimestamp("shipping_date"));
                shipping.setDeliveryDate(rs.getTimestamp("delivery_date"));
                shipping.setStatus(rs.getString("status"));
                shippings.add(shipping);
            }
        }
        return shippings;
    }

    // Cập nhật thông tin giao hàng
    public void updateShipping(Shipping shipping) throws SQLException {
        String sql = "UPDATE shipping SET order_id = ?, shipping_address = ?, shipping_date = ?, delivery_date = ?, status = ? WHERE shipping_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shipping.getOrderId());
            stmt.setString(2, shipping.getShippingAddress());
            stmt.setTimestamp(3, shipping.getShippingDate());
            stmt.setTimestamp(4, shipping.getDeliveryDate());
            stmt.setString(5, shipping.getStatus());
            stmt.setInt(6, shipping.getShippingId());

            stmt.executeUpdate();
        }
    }

    // Xóa thông tin giao hàng
    public void deleteShipping(int shippingId) throws SQLException {
        String sql = "DELETE FROM shipping WHERE shipping_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, shippingId);
            stmt.executeUpdate();
        }
    }
}
