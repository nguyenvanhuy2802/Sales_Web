package org.example.DAO;

// WishlistItemDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.WishlistItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistItemDAO {

    // Thêm sản phẩm vào danh sách yêu thích
    public void addWishlistItem(WishlistItem wishlistItem) throws SQLException {
        String sql = "INSERT INTO wishlist_items (wishlist_id, product_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, wishlistItem.getWishlistId());
            stmt.setInt(2, wishlistItem.getProductId());
            stmt.executeUpdate();

            // Lấy wishlist_item_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                wishlistItem.setWishlistItemId(rs.getInt(1));
            }
        }
    }

    // Lấy sản phẩm trong danh sách yêu thích theo ID
    public WishlistItem getWishlistItemById(int wishlistItemId) throws SQLException {
        String sql = "SELECT * FROM wishlist_items WHERE wishlist_item_id = ?";
        WishlistItem wishlistItem = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistItemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                wishlistItem = new WishlistItem();
                wishlistItem.setWishlistItemId(rs.getInt("wishlist_item_id"));
                wishlistItem.setWishlistId(rs.getInt("wishlist_id"));
                wishlistItem.setProductId(rs.getInt("product_id"));
                wishlistItem.setAddedAt(rs.getTimestamp("added_at"));
            }
        }
        return wishlistItem;
    }

    // Lấy tất cả sản phẩm trong danh sách yêu thích
    public List<WishlistItem> getAllWishlistItems() throws SQLException {
        String sql = "SELECT * FROM wishlist_items";
        List<WishlistItem> wishlistItems = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                WishlistItem wishlistItem = new WishlistItem();
                wishlistItem.setWishlistItemId(rs.getInt("wishlist_item_id"));
                wishlistItem.setWishlistId(rs.getInt("wishlist_id"));
                wishlistItem.setProductId(rs.getInt("product_id"));
                wishlistItem.setAddedAt(rs.getTimestamp("added_at"));
                wishlistItems.add(wishlistItem);
            }
        }
        return wishlistItems;
    }

    // Cập nhật sản phẩm trong danh sách yêu thích
    public void updateWishlistItem(WishlistItem wishlistItem) throws SQLException {
        String sql = "UPDATE wishlist_items SET wishlist_id = ?, product_id = ?, added_at = CURRENT_TIMESTAMP WHERE wishlist_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistItem.getWishlistId());
            stmt.setInt(2, wishlistItem.getProductId());
            stmt.setInt(3, wishlistItem.getWishlistItemId());

            stmt.executeUpdate();
        }
    }

    // Xóa sản phẩm khỏi danh sách yêu thích
    public void deleteWishlistItem(int wishlistItemId) throws SQLException {
        String sql = "DELETE FROM wishlist_items WHERE wishlist_item_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistItemId);
            stmt.executeUpdate();
        }
    }
}
