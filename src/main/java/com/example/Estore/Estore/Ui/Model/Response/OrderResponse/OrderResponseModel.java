package com.example.Estore.Estore.Ui.Model.Response.OrderResponse;

import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class OrderResponseModel {

    private static final long serialVersionUID = 1L;



    public Long orderId;
    private String orderStatus;
    private double orderAmount;
    private AddressesRest billingAddress;
    private AddressesRest shippingAddress;
    //private CartItemRest cartItemRest;
    private LocalDateTime orderedTime;
    private LocalDateTime updatedTime;
    private List<CartItemRest> cartitemEntityList;

//    public List<CartItemEntity> getCartitemEntityList() {
//        return cartitemEntityList;
//    }
//
//    public void setCartitemEntityList(List<CartItemEntity> cartitemEntityList) {
//        this.cartitemEntityList = cartitemEntityList;
//    }


    public List<CartItemRest> getCartitemEntityList() {
        return cartitemEntityList;
    }

    public void setCartitemEntityList(List<CartItemRest> cartitemEntityList) {
        this.cartitemEntityList = cartitemEntityList;
    }

    public LocalDateTime getOrderedTime() {
        return orderedTime;
    }

    public void setOrderedTime(LocalDateTime orderedTime) {
        this.orderedTime = orderedTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

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

