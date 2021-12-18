package com.example.Estore.Estore.io.Entity.WishList;

import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="wishlist")
public class WishListEntity {
    @Id
    @GeneratedValue
    private long wishListId;
    @ManyToOne(cascade = CascadeType.ALL)

    @JsonIgnore
    @Transient
    private ProductEntity productEntity;
    @CreationTimestamp
    private LocalDateTime createdTime;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinTable(name = "wishlist_products",
            joinColumns = {
                    @JoinColumn(name = "wish_list_id", referencedColumnName = "wishListId",
                            nullable = false, updatable = false)},
            inverseJoinColumns = {
                    @JoinColumn(name = "product_id", referencedColumnName = "productId",
                            nullable = false, updatable = false)})
    private List<ProductEntity> productEntityList=new ArrayList<ProductEntity>();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    private UserEntity userEntity;
    private Long userReferenceId;

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

    public Long getUserReferenceId() {
        return userReferenceId;
    }

    public void setUserReferenceId(Long userReferenceId) {
        this.userReferenceId = userReferenceId;
    }

    public List<ProductEntity> getProductEntityList() {
        return productEntityList;
    }

    public void setProductEntityList(List<ProductEntity> productEntityList) {
        this.productEntityList = productEntityList;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}




