package com.example.Estore.Estore.Shared.dto.Cart;

import java.util.Date;

public class CartDto {
    private Long cartItemId;
    private Integer quantity;
    private String productName;
    private double totalPrice;
    private Date createdDate;
    private boolean cartIsActive= true;

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isCartIsActive() {
        return cartIsActive;
    }

    public void setCartIsActive(boolean cartIsActive) {
        this.cartIsActive = cartIsActive;
    }
}
