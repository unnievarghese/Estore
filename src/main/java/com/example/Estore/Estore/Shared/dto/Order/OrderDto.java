package com.example.Estore.Estore.Shared.dto.Order;

import com.example.Estore.Estore.Shared.dto.Cart.CartItemDto;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class OrderDto implements Serializable {

    public Long id;
    private String orderStatus;
    private double orderAmount;
    private AddressDto shippingAddress;
    private AddressDto billingAddress;
    private UserDto userEntity;
    private LocalDateTime orderedTime;
    private LocalDateTime updatedTime;
    private List<CartItemDto> cartitemEntityList;


    public List<CartItemDto> getCartitemEntityList() {
        return cartitemEntityList;
    }

    public void setCartitemEntityList(List<CartItemDto> cartitemEntityList) {
        this.cartitemEntityList = cartitemEntityList;
    }

    public UserDto getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserDto userEntity) {
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
