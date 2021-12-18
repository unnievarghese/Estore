package com.example.Estore.Estore.Ui.Model.Response.OrderResponse;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.CardRest;
import java.time.LocalDate;
import java.util.List;

public class OrderProcessModel {


    private double orderAmount;
    private double totalItemAmount;
    private double tax;
    private double deliverycharge;
    private LocalDate deliverydate;
    private List<CartItemRest> cartitemEntityList;
    private AddressesRest billingAddress;
    private AddressesRest shippingAddress;
    private CardRest cardEntity;

    public CardRest getCardEntity() {
        return cardEntity;
    }

    public void setCardEntity(CardRest cardEntity) {
        this.cardEntity = cardEntity;
    }

    public double getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(double totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
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



    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }



    public List<CartItemRest> getCartitemEntityList() {
        return cartitemEntityList;
    }

    public void setCartitemEntityList(List<CartItemRest> cartitemEntityList) {
        this.cartitemEntityList = cartitemEntityList;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public AddressesRest getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressesRest billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressesRest getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressesRest shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
