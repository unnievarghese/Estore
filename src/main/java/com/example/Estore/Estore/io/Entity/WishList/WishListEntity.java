package com.example.Estore.Estore.io.Entity.WishList;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="wishlist")
public class WishListEntity {
    @Id
    @GeneratedValue
    private long wishListId;


    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity userEntity;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name = "wishlist_products",
                    joinColumns={
            @JoinColumn(name="wishlist_id",referencedColumnName="wishListId",nullable=false,updatable=false)},
                    inverseJoinColumns = {
            @JoinColumn(name = "product_id",referencedColumnName = "productId",nullable = false,updatable = false)})
    @JsonIgnore
    private List<ProductEntity>productEntityList;

    public long getWishListId() {
        return wishListId;
    }

    public void setWishListId(long wishListId) {
        this.wishListId = wishListId;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }
}
