package org.example.DAO;

import org.example.jdbc.DBConnection;
import org.example.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {
    // Thêm đơn hàng mới và trả về ID đơn hàng
    public int addOrderId(Order order) {
        String sql = "INSERT INTO orders (customer_id, buyer_name, total_amount, delivery_address, hash_code) VALUES (?, ?, ?, ?, ?)";
        int generatedOrderId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Thiết lập các giá trị cho tham số
            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getBuyerName());
            stmt.setBigDecimal(3, order.getTotalAmount());
            stmt.setString(4, order.getDeliveryAddress());
            stmt.setString(5, order.getHashCode());

            stmt.executeUpdate();

            // Lấy ID đơn hàng được tự động sinh
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                generatedOrderId = rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return generatedOrderId;
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
                order.setBuyerName(rs.getString("buyer_name"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setHashCode(rs.getString("hash_code"));
                // Thêm giá trị key_id (có thể null)
                order.setKeyId(rs.getObject("key_id", Integer.class));
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
                order.setBuyerName(rs.getString("buyer_name"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setHashCode(rs.getString("hash_code"));
                // Thêm giá trị key_id (có thể null)
                order.setKeyId(rs.getObject("key_id", Integer.class));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }

    // Cập nhật thông tin đơn hàng
    public boolean updateOrder(Order order) {
        String sql = "UPDATE orders SET customer_id = ?, buyer_name = ?, status = ?, total_amount = ?, delivery_address = ?, hash_code = ?, key_id = ? WHERE order_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, order.getCustomerId());
            stmt.setString(2, order.getBuyerName());
            stmt.setString(3, order.getStatus());
            stmt.setBigDecimal(4, order.getTotalAmount());
            stmt.setString(5, order.getDeliveryAddress());
            stmt.setString(6, order.getHashCode());
            // Xử lý key_id (cho phép null)
            if (order.getKeyId() == null) {
                stmt.setNull(7, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(7, order.getKeyId());
            }
            stmt.setInt(8, order.getOrderId());

            // Thực hiện cập nhật và kiểm tra số hàng bị ảnh hưởng
            int rowsAffected = stmt.executeUpdate();

            // Nếu ít nhất một hàng bị ảnh hưởng, trả về true (cập nhật thành công)
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Log ngoại lệ (tùy chọn)
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

    // Lấy danh sách đơn hàng theo trạng thái
    public List<Order> getOrdersByStatus(String status) {
        String sql = "SELECT * FROM orders WHERE status = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setBuyerName(rs.getString("buyer_name"));
                order.setOrderDate(rs.getTimestamp("order_date"));
                order.setStatus(rs.getString("status"));
                order.setTotalAmount(rs.getBigDecimal("total_amount"));
                order.setDeliveryAddress(rs.getString("delivery_address"));
                order.setHashCode(rs.getString("hash_code"));
                // Thêm xử lý thuộc tính keyId
                order.setKeyId(rs.getObject("key_id", Integer.class));
                orders.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orders;
    }


    // Lấy chỉ số AUTO_INCREMENT hiện tại của bảng orders
    public int getCurrentAutoIncrementOrderId() {
        String sql = "SELECT AUTO_INCREMENT " +
                "FROM information_schema.TABLES " +
                "WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?";
        int autoIncrementId = -1;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Thay thế tham số với tên cơ sở dữ liệu và bảng
            stmt.setString(1, conn.getCatalog()); // Tên cơ sở dữ liệu
            stmt.setString(2, "orders"); // Tên bảng

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
