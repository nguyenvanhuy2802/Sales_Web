package org.example.DAO;

// CartItemDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.CartItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartItemDAO {

    // Thêm sản phẩm vào giỏ hàng
    public void addCartItem(CartItem cartItem) {
        String sql = "INSERT INTO cart_items (cart_id, product_id, quantity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());

            stmt.executeUpdate();

            // Lấy cart_item_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                cartItem.setCartItemId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy sản phẩm trong giỏ hàng theo ID
    public CartItem getCartItemById(int cartItemId)  {
        String sql = "SELECT * FROM cart_items WHERE cart_item_id = ?";
        CartItem cartItem = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("cart_item_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setAddedAt(rs.getTimestamp("added_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartItem;
    }

    public CartItem getCartItemByProductId(int productId)  {
        String sql = "SELECT * FROM cart_items WHERE product_id = ?";
        CartItem cartItem = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("cart_item_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setAddedAt(rs.getTimestamp("added_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartItem;
    }

    // Lấy tất cả sản phẩm trong giỏ hàng
    public List<CartItem> getAllCartItems() {
        String sql = "SELECT * FROM cart_items";
        List<CartItem> cartItems = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CartItem cartItem = new CartItem();
                cartItem.setCartItemId(rs.getInt("cart_item_id"));
                cartItem.setCartId(rs.getInt("cart_id"));
                cartItem.setProductId(rs.getInt("product_id"));
                cartItem.setQuantity(rs.getInt("quantity"));
                cartItem.setAddedAt(rs.getTimestamp("added_at"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return cartItems;
    }

    // Cập nhật sản phẩm trong giỏ hàng
    public void updateCartItem(CartItem cartItem)  {
        String sql = "UPDATE cart_items SET cart_id = ?, product_id = ?, quantity = ?, added_at = CURRENT_TIMESTAMP WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItem.getCartId());
            stmt.setInt(2, cartItem.getProductId());
            stmt.setInt(3, cartItem.getQuantity());
            stmt.setInt(4, cartItem.getCartItemId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void deleteCartItem(int cartItemId) {
        String sql = "DELETE FROM cart_items WHERE cart_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, cartItemId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
