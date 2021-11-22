package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryEntity> getCategory() {
        return categoryRepository.findAll();
    }

    @Override
    public List<ProductEntity> getproducts(String name) {
        List<CategoryEntity> category = categoryRepository.findAll();
              List<ProductEntity> productEntity = new ArrayList<>();
        for(CategoryEntity i: category){
            if (i.getCategoryName().equals(name)){
                System.out.println("in loop");
                for(ProductEntity product: i.getProducts()){
                    productEntity.add(product);
                }
            }
        }
        return productEntity;
    }


}
