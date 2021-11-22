package com.example.Estore.Estore.io.Entity.Review;


import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "review")
public class ReviewEntity {
    @Id
    @GeneratedValue
    private Long reviewId;
    private String review;
    private Integer rating;
    @UpdateTimestamp
    private LocalDateTime updatedTime;
    @CreationTimestamp
    private LocalDateTime createdTime;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="product_id",referencedColumnName = "ProductId")
    @JsonIgnore
    private ProductEntity productEntity;
    private String productName;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    public ReviewEntity() {

    }

    public ReviewEntity(String review, Integer rating, ProductEntity productEntity, UserEntity userEntity) {

        this.review = review;
        this.rating = rating;
        this.productEntity = productEntity;
        this.userEntity = userEntity;
    }

    public Long getReviewId() {
        return reviewId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
