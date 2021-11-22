package com.example.Estore.Estore.Shared.dto.Product;

import java.io.Serializable;
import java.util.List;

public class CategoryDto implements Serializable {
    private Long categoryId;
    private String CategoryName;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }
}
