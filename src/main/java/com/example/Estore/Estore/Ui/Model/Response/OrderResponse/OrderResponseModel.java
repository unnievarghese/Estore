package com.example.Estore.Estore.Ui.Model.Response.OrderResponse;

import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


public class OrderResponseModel {

    private static final long serialVersionUID = 1L;



    public Long orderId;
    private String orderStatus;
    private double orderAmount;
    private double totalItemAmount;
    private double tax;
    private double deliverycharge;
    private LocalDate deliverydate;
    private AddressesRest billingAddress;
    private AddressesRest shippingAddress;
    private LocalDateTime orderedTime;
    private LocalDateTime updatedTime;
    private List<CartItemRest> cartitemEntityList;


    public double getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(double totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDeliverycharge() {
        return deliverycharge;
    }

    public void setDeliverycharge(double deliverycharge) {
        this.deliverycharge = deliverycharge;
    }

    public LocalDate getDeliverydate() {
        return deliverydate;
    }

    public void setDeliverydate(LocalDate deliverydate) {
        this.deliverydate = deliverydate;
    }

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

