package com.example.Estore.Estore.io.Entity.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

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
    private Integer quantity;
//    @ManyToOne
//    @JoinColumn(name = "categoryId")
//    @JsonIgnore
//    private CategoryEntity categoryDetails;
//
//    public CategoryEntity getCategoryDetails() {
//        return categoryDetails;
//    }
//
//    public void setCategoryDetails(CategoryEntity categoryDetails) {
//        this.categoryDetails = categoryDetails;
//    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

//    @ManyToMany(mappedBy = "productEntityList", fetch = FetchType.LAZY)
//    @JsonIgnore


    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}