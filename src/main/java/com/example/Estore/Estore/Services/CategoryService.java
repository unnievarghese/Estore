package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {

    List<CategoryEntity> getCategory();
    List<ProductEntity> getproductsByName(String name);
}
