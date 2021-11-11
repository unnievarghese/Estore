package com.example.Estore.Estore.Ui.Model.Response;

import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;

import java.util.List;

public class OrderResponseModel {


    private Long orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressesRest shippingAddress;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
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

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressesRest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressesRest shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
