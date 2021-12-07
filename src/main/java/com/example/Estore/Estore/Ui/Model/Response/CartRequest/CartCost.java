package com.example.Estore.Estore.Ui.Model.Response.CartRequest;

import java.util.List;

public class CartCost {
    private List<CartItemRest> cartItem;
    private double totalCost;
    private String discount;

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<CartItemRest> getCartItem() {
        return cartItem;
    }

    public void setCartItem(List<CartItemRest> cartItem) {
        this.cartItem = cartItem;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}
