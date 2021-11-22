package com.example.Estore.Estore.io.Entity.Product;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity(name="category")
public class CategoryEntity implements Serializable {
    @Id
    @GeneratedValue
    private Long categoryId;
    @Column(nullable = false, length = 100)
    private String categoryName;
    @OneToMany(mappedBy = "categoryDetails", cascade = CascadeType.ALL)
    private List <ProductEntity> products;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}


