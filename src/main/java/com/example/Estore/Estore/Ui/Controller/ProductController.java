package com.example.Estore.Estore.Ui.Controller;
import com.example.Estore.Estore.Services.ProductService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.ProductRest;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="products")
public class ProductController {

    @Autowired
    ProductService productService;
    @GetMapping
public String getProduct()
    {
        return "get product was called";
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })


    @PostMapping
    public ProductRest createProduct(@RequestBody ProductRequestModel productDetails) throws Exception{
        System.out.println("product");
        if (productDetails.getProductName().isEmpty())throw new Exception("Product Not Found");
        ModelMapper modelMapper = new ModelMapper();
        ProductDto productDto = modelMapper.map(productDetails,ProductDto.class);

        ProductDto createProduct = productService.createProduct(productDto);
        ProductRest returnValue= modelMapper.map(createProduct,ProductRest.class);
        return returnValue;

    }

    @PutMapping
    public String updateProduct()
    {
        return "update product was called";
    }
    @DeleteMapping
    public String deleteProduct()
    {
        return "delete product was called";
    }


}
