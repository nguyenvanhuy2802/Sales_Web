package org.example.DAO;

// CategoryDAO.java
import org.example.jdbc.DBConnection;
import org.example.model.Category;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // Thêm danh mục mới
    public void addCategory(Category category)  {
        String sql = "INSERT INTO categories (name, description, category_image) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getCategoryImage());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Lấy danh mục theo ID
    public Category getCategoryById(int categoryId)  {
        String sql = "SELECT * FROM categories WHERE category_id = ?";
        Category category = null;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setCategoryImage(rs.getString("category_image"));
                category.setCreatedAt(rs.getTimestamp("created_at"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return category;
    }

    // Lấy tất cả danh mục
    public List<Category> getAllCategories()  {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Category category = new Category();
                category.setCategoryId(rs.getInt("category_id"));
                category.setName(rs.getString("name"));
                category.setDescription(rs.getString("description"));
                category.setCategoryImage(rs.getString("category_image"));
                category.setCreatedAt(rs.getTimestamp("created_at"));
                categories.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return categories;
    }

    // Cập nhật thông tin danh mục
    public void updateCategory(Category category) {
        String sql = "UPDATE categories SET name = ?, description = ?, category_image = ? WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, category.getName());
            stmt.setString(2, category.getDescription());
            stmt.setString(3, category.getCategoryImage());
            stmt.setInt(4, category.getCategoryId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Xóa danh mục
    public void deleteCategory(int categoryId)  {
        String sql = "DELETE FROM categories WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, categoryId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
