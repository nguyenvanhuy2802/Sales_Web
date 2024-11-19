package org.example.model;

import java.sql.Timestamp;

public class WishlistItem {
    private int wishlistItemId;
    private int wishlistId;
    private int productId;
    private Timestamp addedAt;

    public WishlistItem() {}

    public WishlistItem(int wishlistId, int productId) {
        this.wishlistId = wishlistId;
        this.productId = productId;
    }

    public int getWishlistItemId() {
        return wishlistItemId;
    }

    public void setWishlistItemId(int wishlistItemId) {
        this.wishlistItemId = wishlistItemId;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public Timestamp getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(Timestamp addedAt) {
        this.addedAt = addedAt;
    }
}
