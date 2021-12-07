package com.example.Estore.Estore.io.Entity.Order;

import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.AddressEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
public class OrderEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    private Long orderId;

    @Column(nullable = false, length = 120)
    private String orderStatus;

    @Column(nullable = false)
    private double orderAmount;

    @Column
    @ElementCollection
    private List<CartItemEntity> cartitemEntityList=new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address")
    private AddressEntity billingAddress;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address")
    private AddressEntity shippingAddress;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;

    private LocalDateTime orderedTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;


    public List<CartItemEntity> getCartitemEntityList() {
        return cartitemEntityList;
    }

    public void setCartitemEntityList(List<CartItemEntity> cartitemEntityList) {
        this.cartitemEntityList = cartitemEntityList;
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

    public AddressEntity getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(AddressEntity billingAddress) {
        this.billingAddress = billingAddress;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
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
}

