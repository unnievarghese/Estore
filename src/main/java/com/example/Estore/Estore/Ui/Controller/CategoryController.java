package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
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

    @GetMapping(path="/category-all")
    public ResponseEntity <CategoryEntity>getCategory (){
        List<CategoryEntity> categories = categoryService.getCategory();
        return new ResponseEntity(categories, HttpStatus.OK);
    }

    @GetMapping(path ="/{name}")
    public List<ProductEntity> getproducts(@PathVariable("name") String name){
        return categoryService.getproducts(name);

    }

}
