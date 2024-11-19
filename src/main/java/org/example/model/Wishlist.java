package org.example.model;

import java.sql.Timestamp;

public class Wishlist {
    private int wishlistId;
    private int customerId;
    private Timestamp createdAt;

    public Wishlist() {}

    public Wishlist(int customerId) {
        this.customerId = customerId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
