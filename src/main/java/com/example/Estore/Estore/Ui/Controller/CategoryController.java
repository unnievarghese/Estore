package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    //http://localhost:8080/estore/category/category-all
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @GetMapping(path="/category-all")
    public ResponseEntity <CategoryEntity>getCategory (){
        List<CategoryEntity> categories = categoryService.getCategory();
        return new ResponseEntity(categories, HttpStatus.OK);
    }


    //http://localhost:8080/estore/category/{name}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path ="/{name}")
    public List<ProductEntity> getproductsByName(@PathVariable("name") String name){
        return categoryService.getproductsByName(name);

    }

}
