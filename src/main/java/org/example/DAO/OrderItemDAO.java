package org.example.DAO;

import org.example.jdbc.DBConnection;
import org.example.model.OrderItem;
import org.example.model.OrderItemDTO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.example.jdbc.DBConnection.getConnection;

public class OrderItemDAO {

    // Thêm chi tiết đơn hàng mới
    public void addOrderItem(OrderItem orderItem)  {
        String sql = "INSERT INTO order_items (order_id, product_id, quantity, price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getPrice());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy chi tiết đơn hàng theo ID
    public OrderItem getOrderItemById(int orderItemId)  {
        String sql = "SELECT * FROM order_items WHERE order_item_id = ?";
        OrderItem orderItem = null;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setProductId(rs.getInt("product_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getBigDecimal("price"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderItem;
    }

    // Lấy tất cả chi tiết đơn hàng
    public List<OrderItem> getAllOrderItems()  {
        String sql = "SELECT * FROM order_items";
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setProductId(rs.getInt("product_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getBigDecimal("price"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderItems;
    }

    // Cập nhật thông tin chi tiết đơn hàng
    public void updateOrderItem(OrderItem orderItem)  {
        String sql = "UPDATE order_items SET order_id = ?, product_id = ?, quantity = ?, price = ? WHERE order_item_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItem.getOrderId());
            stmt.setInt(2, orderItem.getProductId());
            stmt.setInt(3, orderItem.getQuantity());
            stmt.setBigDecimal(4, orderItem.getPrice());
            stmt.setInt(5, orderItem.getOrderItemId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa chi tiết đơn hàng
    public void deleteOrderItem(int orderItemId)  {
        String sql = "DELETE FROM order_items WHERE order_item_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderItemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy danh sách chi tiết đơn hàng theo order ID
    public List<OrderItem> getOrderItemsByOrderId(int orderId) {
        String sql = "SELECT * FROM order_items WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getInt("order_item_id"));
                orderItem.setOrderId(rs.getInt("order_id"));
                orderItem.setProductId(rs.getInt("product_id"));
                orderItem.setQuantity(rs.getInt("quantity"));
                orderItem.setPrice(rs.getBigDecimal("price"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orderItems;
    }

    public List<OrderItemDTO> getOrderItemsWithProductByOrderId(int orderId) {
        List<OrderItemDTO> orderItems = new ArrayList<>();
        String sql = "SELECT oi.order_item_id, oi.order_id, oi.product_id, p.name, p.product_image, oi.quantity, oi.price " +
                "FROM order_items oi " +
                "JOIN products p ON oi.product_id = p.product_id " +
                "WHERE oi.order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderItemDTO item = new OrderItemDTO();
                    item.setOrderItemId(rs.getInt("order_item_id"));
                    item.setOrderId(rs.getInt("order_id"));
                    item.setProductId(rs.getInt("product_id"));
                    item.setProductName(rs.getString("name"));
                    item.setProductImage(rs.getString("product_image"));
                    item.setQuantity(rs.getInt("quantity"));
                    item.setPrice(rs.getBigDecimal("price"));
                    orderItems.add(item);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    // Lấy chỉ số AUTO_INCREMENT hiện tại của bảng order_items
    public int getCurrentAutoIncrementOrderItemId() {
        String sql = "SELECT AUTO_INCREMENT " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        int autoIncrementId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thay thế tham số với tên cơ sở dữ liệu và bảng
            stmt.setString(1, conn.getCatalog()); // Tên cơ sở dữ liệu
            stmt.setString(2, "order_items"); // Tên bảng

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                autoIncrementId = rs.getInt("AUTO_INCREMENT");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return autoIncrementId;
    }

}
