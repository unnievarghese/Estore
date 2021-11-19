package com.example.Estore.Estore.io.Entity.Product;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@Table(name="products")

public class ProductEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long ProductId;


    @Column(nullable = false)
    private String productName;
    private String description;
    @Column(nullable = false)
    private Integer price;
    @Column(nullable = false)
    private Integer quantity;



    public Long getProductId() {
        return ProductId;
    }

    public void setProductId(Long productId) {
        ProductId = productId;
    }

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
