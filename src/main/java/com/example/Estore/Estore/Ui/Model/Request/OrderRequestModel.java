package com.example.Estore.Estore.Ui.Model.Request;

public class OrderRequestModel {

    private Long shippingAddress;
    private Long billingAddress;

    public Long getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(Long shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Long getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Long billingAddress) {
        this.billingAddress = billingAddress;
    }
}
