package org.example.DAO;

// WishlistDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.Wishlist;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {

    // Thêm danh sách yêu thích mới
    public void addWishlist(Wishlist wishlist) throws SQLException {
        String sql = "INSERT INTO wishlist (customer_id) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, wishlist.getCustomerId());
            stmt.executeUpdate();

            // Lấy wishlist_id được tạo tự động
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                wishlist.setWishlistId(rs.getInt(1));
            }
        }
    }

    // Lấy danh sách yêu thích theo ID
    public Wishlist getWishlistById(int wishlistId) throws SQLException {
        String sql = "SELECT * FROM wishlist WHERE wishlist_id = ?";
        Wishlist wishlist = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                wishlist = new Wishlist();
                wishlist.setWishlistId(rs.getInt("wishlist_id"));
                wishlist.setCustomerId(rs.getInt("customer_id"));
                wishlist.setCreatedAt(rs.getTimestamp("created_at"));
            }
        }
        return wishlist;
    }

    // Lấy tất cả danh sách yêu thích
    public List<Wishlist> getAllWishlists() throws SQLException {
        String sql = "SELECT * FROM wishlist";
        List<Wishlist> wishlists = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Wishlist wishlist = new Wishlist();
                wishlist.setWishlistId(rs.getInt("wishlist_id"));
                wishlist.setCustomerId(rs.getInt("customer_id"));
                wishlist.setCreatedAt(rs.getTimestamp("created_at"));
                wishlists.add(wishlist);
            }
        }
        return wishlists;
    }

    // Cập nhật danh sách yêu thích
    public void updateWishlist(Wishlist wishlist) throws SQLException {
        String sql = "UPDATE wishlist SET customer_id = ?, created_at = CURRENT_TIMESTAMP WHERE wishlist_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlist.getCustomerId());
            stmt.setInt(2, wishlist.getWishlistId());

            stmt.executeUpdate();
        }
    }

    // Xóa danh sách yêu thích
    public void deleteWishlist(int wishlistId) throws SQLException {
        String sql = "DELETE FROM wishlist WHERE wishlist_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, wishlistId);
            stmt.executeUpdate();
        }
    }
}

