package com.example.Estore.Estore.Ui.Model.Response.WishListRequest;

import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;

import java.util.List;

public class WishListRest {
    private String wishListId;
    private String userId;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }



    public String getWishListId() {
        return wishListId;
    }

    public void setWishListId(String wishListId) {
        this.wishListId = wishListId;
    }






}


