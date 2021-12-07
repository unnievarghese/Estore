package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.CategoryRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public ProductDto createProduct(ProductDto product)
    {
        ProductEntity productName = productRepository.findByProductName(product.getProductName());
        if(productName != null)
            throw new ClientSideException(Messages.PRODUCT_ALREADY_EXISTS.getMessage());
        ModelMapper modelMapper = new ModelMapper();
        ProductEntity productEntity = modelMapper.map(product, ProductEntity.class);
        CategoryEntity categoryEntity = categoryRepository.findByCategoryName(product.getCategoryName());
        productEntity.setCategoryDetails(categoryEntity);
        /*
        checks for the category if category not present in categoryEntity exception occures
         */
        if (categoryEntity == null) throw new ClientSideException(Messages.CATEGORY_NOT_FOUND.getMessage());

        ProductEntity storedProductDetails = productRepository.save(productEntity);
        ProductDto returnValue = modelMapper.map(storedProductDetails, ProductDto.class);
        return returnValue;
    }

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
       return returnValue;
   }

    @Override
    public ProductEntity findByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public ProductEntity findByProductId(Long productId) {
        return productRepository.findByProductId(productId);
    }

    @Override
    public void deleteProduct(Long productId) throws Exception
    {
        ProductEntity product = productRepository.findByProductId(productId);
        if (product == null) throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        if (productRepository.getById(productId).getProductId().equals(productId)){
            productRepository.deleteById(productId);
     }

    }

    @Override
    public ProductEntity updateProduct(Long productId, ProductRequestModel productDetails) throws ClientSideException {

       ProductEntity product = productRepository.findByProductId(productId);

       if (product == null) throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

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
    public CategoryRest addCategory(CategoryRequestModel categoryRequestModel)  {
        CategoryEntity category = categoryRepository.findByCategoryName(categoryRequestModel.getCategoryName());
        if (category!=null) throw new ClientSideException(Messages.RECORD_ALREADY_EXISTS.getMessage());
        CategoryEntity categoryEntity = new ModelMapper().map(categoryRequestModel,CategoryEntity.class);
        categoryRepository.save(categoryEntity);

        return new ModelMapper().map(categoryEntity,CategoryRest.class);
    }

}

