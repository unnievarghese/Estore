package com.example.Estore.Estore.Ui.Model.Response.WishListRequest;

import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import java.util.List;

public class WishListRest {
    private String wishListId;
    private String userId;
    List<ProductEntity> productEntitylist;

    public List<ProductEntity> getProductEntitylist() {
        return productEntitylist;
    }

    public void setProductEntitylist(List<ProductEntity> productEntitylist) {
        this.productEntitylist = productEntitylist;
    }

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


