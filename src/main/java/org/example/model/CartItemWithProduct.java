package org.example.model;

public class CartItemWithProduct {
    private CartItem cartItem;
    private Product product;

    public CartItemWithProduct(CartItem cartItem, Product product){
        this.cartItem = cartItem;
        this.product = product;
    }

    public CartItem getCartItem() {
        return cartItem;
    }

    public void setCartItem(CartItem cartItem) {
        this.cartItem = cartItem;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
