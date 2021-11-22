package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.ErrorMessages;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.CategoryRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto product) {
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(product.getCategoryName());
        productEntity.setCategoryDetails(categoryEntity);
        ProductEntity storedProductDetails = productRepository.save(productEntity);
        ProductDto returnValue = modelMapper.map(storedProductDetails, ProductDto.class);
        return returnValue;
    }

    @Override
    public List<ProductEntity> getProducts() {
        return (List<ProductEntity>) productRepository.findAll();
    }
    @Override
    public ProductEntity findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public void deleteProduct(Long productId) throws Exception
    {
        if (productRepository.getById(productId).getProductId().equals(productId)){
            productRepository.deleteById(productId);
        }

    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductRequestModel productDetails) throws Exception {

       ProductEntity product = productRepository.findByProductId(productId);

       if (product.getProductId().equals(null)) throw new Exception(String.valueOf(ErrorMessages.NO_RECORD_FOUND));

       else

           product.setProductName(productDetails.getProductName());
           product.setPrice(productDetails.getPrice());
           product.setDescription(productDetails.getDescription());
           product.setQuantity(productDetails.getQuantity());
           CategoryEntity categoryEntity = categoryRepository.findByCategoryName(productDetails.getCategoryName());
           product.setCategoryDetails(categoryEntity);
           System.out.println(categoryEntity);
           productRepository.save(product);
           return product;

    }

    @Override
    public CategoryRest addCategory(CategoryRequestModel categoryRequestModel) {
        CategoryEntity categoryEntity = new ModelMapper().map(categoryRequestModel,CategoryEntity.class);
        categoryRepository.save(categoryEntity);
        return new ModelMapper().map(categoryEntity,CategoryRest.class);
    }

}

