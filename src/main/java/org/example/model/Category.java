package org.example.model;

import java.sql.Timestamp;

public class Category {
    private int categoryId;
    private String name;
    private String description;
    private String categoryImage;
    private Timestamp createdAt;

    public Category() {
    }

    public Category(String name, String description, String categoryImage) {
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
    }

    public Category(int categoryId, String name, String description, String categoryImage, Timestamp createdAt) {
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.categoryImage = categoryImage;
        this.createdAt = createdAt;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
