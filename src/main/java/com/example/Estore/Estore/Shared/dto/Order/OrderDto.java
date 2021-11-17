package com.example.Estore.Estore.Shared.dto.Order;

import com.example.Estore.Estore.Shared.dto.User.AddressDto;

import java.io.Serializable;

public class OrderDto implements Serializable {

    private long orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    //private CartDto cartDto;




    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressDto getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressDto shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public AddressDto getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressDto billingAddress) {
        this.billingAddress = billingAddress;
    }

//    public CartDto getCartDto() {
//        return cartDto;
//    }
//
//    public void setCartDto(CartDto cartDto) {
//        this.cartDto = cartDto;
//    }
}
