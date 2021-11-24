package com.example.Estore.Estore.Ui.Controller;
import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.ProductRest;

import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="products")
public class ProductController {

    @Autowired
    ProductService productService;

    //http://localhost:8080/estore/products/get-products
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @GetMapping(path = "/get-products")
    public ResponseEntity<ProductEntity> getProducts(){
        List<ProductEntity> productEntityList = productService.getProducts();
        return new ResponseEntity(productEntityList,HttpStatus.OK);
    }

    //http://localhost:8080/estore/products/find/{id}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("id")Long productId){
        ProductEntity productEntityList = productService.findByProductId(productId);
        return new ResponseEntity(productEntityList,HttpStatus.FOUND);
    }

    //http://localhost:8080/estore/products/create

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PostMapping(path="/create")
    public ProductRest createProduct(@RequestBody ProductRequestModel productDetails){

        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productDetails,ProductDto.class);

        ProductDto createProduct = productService.createProduct(productDto);
        ProductRest returnValue= modelMapper.map(createProduct,ProductRest.class);
        return returnValue;

    }

    //http://localhost:8080/estore/products/update/{id}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path ="/update/{id}")

    public ProductRest updateProduct(@PathVariable("id") Long productId,@RequestBody ProductRequestModel productDetails)
            throws Exception {

        ProductEntity updateProduct = productService.updateProduct(productId,productDetails);
                return new ModelMapper().map(updateProduct,ProductRest.class);

    }

    //http://localhost:8080/estore/products/delete/{id}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @DeleteMapping(path="/delete/{id}")
    public  ResponseEntity deleteProduct(@PathVariable ("id")Long productId) throws Exception {
        productService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //http://localhost:8080/estore/products/category-add

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PostMapping(path="/category-add")
    public CategoryRest addCategory(@RequestBody CategoryRequestModel categoryRequestModel)
    {
        CategoryRest category = productService.addCategory(categoryRequestModel);
        return category;
    }


}
