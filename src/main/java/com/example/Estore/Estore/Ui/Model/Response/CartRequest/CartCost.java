package com.example.Estore.Estore.Ui.Model.Response.CartRequest;

import java.util.List;

public class CartCost {
    private List<CartItemRest> cartItem;
    private double subTotal;
    private String discount;
    private String deliveryCharge;



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

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public String getDeliveryCharge() {
        return deliveryCharge;
    }

    public void setDeliveryCharge(String deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }
}
