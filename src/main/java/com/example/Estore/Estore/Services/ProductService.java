package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto product);
    List<ProductEntity> getProducts();
    ProductEntity findByProductId(Long productId);
    void deleteProduct(Long productId) throws Exception;
    ProductEntity updateProduct(Long productId, ProductRequestModel productDetails) throws Exception;
    CategoryRest addCategory(CategoryRequestModel categoryRequestModel);
}



