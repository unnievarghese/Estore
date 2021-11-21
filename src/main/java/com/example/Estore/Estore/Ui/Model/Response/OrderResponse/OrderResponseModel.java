package com.example.Estore.Estore.Ui.Model.Response.OrderResponse;

import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;


public class OrderResponseModel {

    private static final long serialVersionUID = 1L;



    public Long orderId;
    //public String orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressesRest billingAddress;
    private AddressesRest shippingAddress;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }


    //public String getOrderId() {
    //    return orderId;
    //}

    //public void setOrderId(String orderId) {
        //this.orderId = orderId;
    //}

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

    public AddressesRest getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressesRest billingAddress) {
        this.billingAddress = billingAddress;
    }
}

