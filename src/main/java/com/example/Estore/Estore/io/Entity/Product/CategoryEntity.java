package com.example.Estore.Estore.io.Entity.Product;


import javax.persistence.*;
import java.io.Serializable;
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

    /*
    To get category id
     */
    public Long getCategoryId() {
        return categoryId;
    }
    /*
    To set category id
     */
    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
    /*
        To get category name
         */
    public String getCategoryName() {
        return categoryName;
    }
    /*
        To set category name
         */
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    /*
        To get list of products
         */
    public List<ProductEntity> getProducts() {
        return products;
    }
    /*
        To set list of products
         */
    public void setProducts(List<ProductEntity> products) {
        this.products = products;
    }
}


