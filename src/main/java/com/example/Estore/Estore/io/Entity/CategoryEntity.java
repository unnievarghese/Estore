package com.example.Estore.Estore.io.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = 1354319275721004002L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String categoryId;

    @Column(nullable = false,length = 50)
    private String name;

    @OneToMany(mappedBy = "categoryDetails",cascade = CascadeType.ALL)
    private List<ItemEntity> Item;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ItemEntity> getItem() {
        return Item;
    }

    public void setItem(List<ItemEntity> item) {
        Item = item;
    }
}
