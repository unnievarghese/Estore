package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {

    List<CategoryEntity> getCategory(int page, int limit);
    List<ProductEntity> getproductsByCategoryName(String name);
    void deleteCategory(Long categoryId) throws Exception;

}
