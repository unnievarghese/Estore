package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.CategoryRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    /**
     * Inject ProductRepository dependency
     */
    @Autowired
    ProductRepository productRepository;
    /**
     * Inject CategoryRepository dependency
     */
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    UserService userService;

    /**
     * Method to add a new product to database
     * @param product contains dto of product details
     * @return ProductDto
     */
    @Override
    public ProductDto createProduct(ProductDto product) {
        ProductEntity productName = productRepository.findByProductName(product.getProductName());
        if (productName != null)
            throw new ClientSideException(Messages.PRODUCT_ALREADY_EXISTS_UPDATE.getMessage());
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(product.getCategoryName());
        productEntity.setCategoryDetails(categoryEntity);

        /*
        checks for the category if category not present in categoryEntity exception occures
         */
        if (categoryEntity == null) throw new ClientSideException(Messages.CATEGORY_NOT_FOUND.getMessage());
        String  auth = SecurityContextHolder.getContext().getAuthentication().getName();
        String sellerId = userService.getUser(auth).getUserId();
        productEntity.setSellerId(sellerId);
        ProductEntity storedProductDetails = productRepository.save(productEntity);
        ProductDto returnValue = modelMapper.map(storedProductDetails, ProductDto.class);
        return returnValue;
    }


    /**
     * Method to fetch all products
     * @param page  page to be indexed
     * @param limit number of categories to be displayed in a page
     * @return ProductDto
     */
    @Override
    public List<ProductDto> getProducts(int page, int limit)
    {
        List<ProductDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<ProductEntity> productsPage = productRepository.findAll(pageableRequest);
        List<ProductEntity> products = productsPage.getContent();

        for (ProductEntity productEntity : products){
            ProductDto productDto = new ModelMapper().map(productEntity,ProductDto.class);
            returnValue.add(productDto);
        }
        if (products.isEmpty())
            throw new ClientSideException(Messages.PRODUCTS_IS_EMPTY.getMessage());
        return returnValue;
    }

    /**
     * Method to find product by passing productName
     * @param productName contains productname
     * @return ProductEntity
     */
    @Override
    public ProductEntity findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    /**
     * Method to find product by passing productId
     * @param productId contains the unique Long id auto-generated for each product
     * @return ProductEntity
     */
    @Override
    public ProductEntity findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }

    /**
     * Method to delete a product pby passing an Id
     * @param productId contains the unique Long id auto-generated for each product
     * @throws Exception throws custom exception
     */
    @Override
    public void deleteProduct(Long productId) throws Exception
    {
        ProductEntity product = productRepository.findByProductId(productId);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        if (!user.getUserId().equals(product.getSellerId()))
            throw new ClientSideException(Messages.NO_ACCESS.getMessage());
        if (product == null) throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        if (productRepository.getById(productId).getProductId().equals(productId)){
            productRepository.deleteById(productId);
        }

    }

    /**
     * Method to update product details by passing productId
     * @param productId contains the unique Long id auto-generated for each product
     * @param productDetails contains the details to be updated to the product.
     * @return ProductEntity
     * @throws ClientSideException
     */

    @Override

    public ProductEntity updateProduct( Long productId, ProductRequestModel productDetails) throws ClientSideException {

        ProductEntity product = productRepository.findByProductId(productId);
        product.setProductName(productDetails.getProductName());
        product.setPrice(productDetails.getPrice());
        product.setDescription(productDetails.getDescription());
        product.setQuantity(productDetails.getQuantity());
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(productDetails.getCategoryName());
        product.setCategoryDetails(categoryEntity);
        productRepository.save(product);
        return product;

    }

}