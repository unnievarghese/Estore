package com.example.Estore.Estore.io.Entity.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity

@Table(name="products")

public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long productId;
    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    @JsonIgnore
    private Integer quantity;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    @JsonIgnore
    private CategoryEntity categoryDetails;
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updatedTime;
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createTime;
    @Column(nullable = false)
    @JsonIgnore
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public ProductEntity() {
    }
    public CategoryEntity getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(CategoryEntity categoryDetails) {
        this.categoryDetails = categoryDetails;
    }
    /*
        To get product id
         */
    public Long getProductId() {
        return productId;
    }
    /*
        To set product id
         */
    public void setProductId(Long productId) {
        this.productId = productId;
    }
/*
    To get product name
     */
    public String getProductName() {
        return productName;
    }

    /*
    To set product name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /*
    To get product description
     */
    public String getDescription() {
        return description;
    }

    /*
    To set product description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /*
    To get product price
     */
    public Integer getPrice() {
        return price;
    }

    /*
    To set product price
     */
    public void setPrice(Integer price) {
        this.price = price;
    }
    /*
        To get quantity of product
         */
    public Integer getQuantity() {
        return quantity;
    }
    /*
        To set quantity of product
         */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}