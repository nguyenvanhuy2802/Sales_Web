package org.example.DAO;

// CartDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.Cart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartDAO {

    // Thêm giỏ hàng mới
    public void addCart(Cart cart) {
        String sql = "INSERT INTO carts (customer_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cart.getCustomerId());
            stmt.executeUpdate();

            // Lấy cart_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cart.setCartId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy giỏ hàng theo ID
    public Cart getCartById(int cartId) {
        String sql = "SELECT * FROM carts WHERE cart_id = ?";
        Cart cart = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setCustomerId(rs.getInt("customer_id"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                cart.setUpdatedAt(rs.getTimestamp("updated_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cart;
    }

    // Lấy tất cả giỏ hàng
    public List<Cart> getAllCarts() {
        String sql = "SELECT * FROM carts";
        List<Cart> carts = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setCustomerId(rs.getInt("customer_id"));
                cart.setCreatedAt(rs.getTimestamp("created_at"));
                cart.setUpdatedAt(rs.getTimestamp("updated_at"));
                carts.add(cart);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return carts;
    }

    // Cập nhật giỏ hàng
    public void updateCart(Cart cart)  {
        String sql = "UPDATE carts SET customer_id = ?, updated_at = CURRENT_TIMESTAMP WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cart.getCustomerId());
            stmt.setInt(2, cart.getCartId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa giỏ hàng
    public void deleteCart(int cartId) {
        String sql = "DELETE FROM carts WHERE cart_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
