package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;

    @Override
    public ProductDto createProduct(ProductDto product) {
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(product,ProductEntity.class);
        ProductEntity storedProductDetails =  productRepository.save(productEntity);
        ProductDto returnValue = modelMapper.map(storedProductDetails,ProductDto.class);
        return returnValue;
    }
}