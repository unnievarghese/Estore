package com.example.Estore.Estore.Ui.Controller;
import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Exception.Validation;
import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;

import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.ProductRest;


import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="products")
public class ProductController {

    @Autowired
    ProductService productService;
    @Autowired
    ProductRepository productRepository;

    /**
     * Method is used to create product. Only seller can create product.
     * @param productDetails, contains necessary fields to create a product.
     * @return returnValue
     */
    //http://localhost:8080/estore/products/create

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @PostMapping(path="/create")
    public ResponseEntity<ProductRest> createProduct(@RequestBody ProductRequestModel productDetails)  {
        Validation validate = new Validation();
        /*
        checks for any missing field
         */
        if (!validate.checkFields(productDetails)) throw new ClientSideException
                (Messages.MISSING_REQUIRED_FIELD.getMessage());

        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productDetails,ProductDto.class);

        ProductDto createProduct = productService.createProduct(productDto);
        ProductRest returnValue= modelMapper.map(createProduct,ProductRest.class);
        return new ResponseEntity(returnValue,HttpStatus.CREATED) ;

    }



    /*
Update product details by productId
*/



    /**
     * Method is used to update details of a single product.
     * @param productId, is of Long type an auto-incremented value.
     * @param productDetails
     * @return updated product
     * @throws Exception,throws custom exception
     */

    //http://localhost:8080/estore/products/update/{id}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @PutMapping(path ="/update/{id}")

    public ProductRest updateProduct(@PathVariable("id") Long productId,@RequestBody ProductRequestModel productDetails)
            throws Exception {
        ProductEntity updateProduct = productService.updateProduct(productId,productDetails);
        return new ModelMapper().map(updateProduct,ProductRest.class);
    }




    /*
Get all products
*/



    /**
     * Method is used to fetch all products.
     * @param page
     * @param limit
     * @return returnValue, list of all products
     */
    //http://localhost:8080/estore/products/get-products
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping(path = "/get-products")

    public ResponseEntity <ProductRest>getProducts(@RequestParam(value = "page",defaultValue = "0") int page,
                                                   @RequestParam(value = "limit",defaultValue = "2")int limit)
    {
        List<ProductRest> returnValue = new ArrayList<>();
        List<ProductDto> products = productService.getProducts(page,limit);

        for (ProductDto productDto : products){
            ProductRest productRest = new ModelMapper().map(productDto,ProductRest.class);
            returnValue.add(productRest);
        }
        return new  ResponseEntity(returnValue,HttpStatus.OK);



    }
/*
Find product by productId
*/



    /**
     * Method is used to fetch product by productId
     * @param productId is of Long type,an auto-incremented value.
     * @return ProductEntity
     * @throws Exception,throws custom exception
     */
    //http://localhost:8080/estore/products/find/{id}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @GetMapping(path = "/find/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("id")Long productId) throws Exception{

        ProductEntity productEntity = productService.findByProductId(productId);
        if (productEntity == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        return new ResponseEntity(productEntity, HttpStatus.OK);
    }



    /**
     * Method is used to fetch product by its product name
     * @param productName is of String type
     * @return productEntity, details of corresponding product name is returned
     * @throws ClientSideException, throws custom exception
     */
    //http://localhost:8080/estore/products/find/productname/{name}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured({"ROLE_ADMIN","ROLE_BUYER"})
    @GetMapping(path = "/find/productname/{name}")
    public ResponseEntity<ProductEntity> getProductByProductName(@PathVariable("name")String productName) throws ClientSideException{

        ProductEntity productEntity = productService.findByProductName(productName);
        if (productEntity == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.name());

        return new ResponseEntity(productEntity, HttpStatus.OK);
    }

/*
Delete product by productId
*/


    /**
     * Method is used to delete product by productId
     * @param productId,
     * @return Record deleted successfully message.
     * @throws Exception,throws custom exception
     */
    //http://localhost:8080/estore/products/delete/{id}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured({"ROLE_ADMIN", "ROLE_SELLER"})
    @DeleteMapping(path="/delete/{id}")
    public  ResponseEntity deleteProduct(@PathVariable ("id")Long productId) throws Exception {
        productService.deleteProduct(productId);
        return new ResponseEntity<String>(Messages.DELETE_SUCCESS.getMessage(),HttpStatus.OK);
    }
/*
//Add Category
*/

    /**
     * Method is used to create product category
     * @param categoryRequestModel, contains category name
     * @return category
     * @throws Exception
     */
    //http://localhost:8080/estore/products/category-add

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path="/category-add")
    public ResponseEntity<CategoryRest> addCategory(@RequestBody CategoryRequestModel categoryRequestModel) throws Exception
    {

        CategoryRest category = productService.addCategory(categoryRequestModel);
        return new  ResponseEntity(category,HttpStatus.CREATED);

    }


}