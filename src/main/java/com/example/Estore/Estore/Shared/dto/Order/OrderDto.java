package com.example.Estore.Estore.Shared.dto.Order;

import com.example.Estore.Estore.Shared.dto.User.AddressDto;

import java.io.Serializable;

public class OrderDto implements Serializable {

    public Long id;
    private String orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    //private CartDto cartDto;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
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
