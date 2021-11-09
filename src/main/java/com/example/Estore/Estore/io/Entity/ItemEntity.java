package com.example.Estore.Estore.io.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Item")
public class ItemEntity implements Serializable {

    private static final long serialVersionUID = -7938447234427184782L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false,length = 50)
    private String name;
    @Column(nullable = false,length = 20)
    private Integer price;
    @Column(nullable = false,length = 250)
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CategoryEntity getCategoryDetails() {
        return categoryDetails;
    }

    public void setCategoryDetails(CategoryEntity categoryDetails) {
        this.categoryDetails = categoryDetails;
    }

}
