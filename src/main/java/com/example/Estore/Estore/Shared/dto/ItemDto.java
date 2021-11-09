package com.example.Estore.Estore.Shared.dto;

import javax.persistence.Column;
import java.io.Serializable;

public class ItemDto implements Serializable {

    private static final long serialVersionUID = 2325217956698586216L;
    private String itemId;
    private String name;
    private Integer price;
    private String description;
    private String category;

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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
