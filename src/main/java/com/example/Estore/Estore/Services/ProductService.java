package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto product);
    ProductEntity findByProductId(Long productId);
    void deleteProduct(Long productId) throws Exception;
    ProductEntity updateProduct( Long productId, ProductRequestModel productDetails) throws Exception;
    List<ProductDto> getProducts(int page, int limit);
    ProductEntity findByProductName(String productName);
}



