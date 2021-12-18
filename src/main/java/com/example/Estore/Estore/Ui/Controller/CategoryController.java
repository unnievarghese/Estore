package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping(path = "category")
public class CategoryController {

    /**
     * Inject CategoryService dependency
     */
    @Autowired
    CategoryService categoryService;
    /*
Add Category
*/

    /**
     * Method is used to create product category
     * @param categoryRequestModel, contains category name
     * @return CategoryRest
     * @throws Exception
     */
    //http://localhost:8080/estore/category/category-add
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @Secured("ROLE_ADMIN")
    @PostMapping(path="/category-create")
    public ResponseEntity<CategoryRest> createCategory(@RequestBody CategoryRequestModel categoryRequestModel) throws Exception {

        CategoryRest category = categoryService.addCategory(categoryRequestModel);
        return new ResponseEntity(category, HttpStatus.CREATED);

    }
/*
Get all categories
 */

    /**
     * Method is used to fetch all categories.
     * @return CategoryEntity
     * @throws ClientSideException, throws custom exception
     */
    //http://localhost:8080/estore/category/category-all
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured({"ROLE_ADMIN", "ROLE_SELLER", "ROLE_BUYER"})
    @GetMapping(path="/category-all")
    public ResponseEntity <CategoryEntity>viewCategories (@RequestParam(value = "page",defaultValue = "0") int page,
                                                       @RequestParam(value = "limit",defaultValue = "2")int limit)
            throws ClientSideException{
        List<CategoryEntity> categories = categoryService.getCategory(page,limit);
        if(categories==null)throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());
        return new ResponseEntity(categories, HttpStatus.OK);
    }
/*
find product by category name
 */

    /**
     * Method is used to category by category name.
     * @param name, category name is of String type
     * @return ProductEntity, list of products in given category name.
     */
    //http://localhost:8080/estore/category/{name}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured({"ROLE_ADMIN", "ROLE_SELLER","ROLE_BUYER"})
    @GetMapping(path ="/{name}")
    public List<ProductEntity> viewProductsByCategoryName(@PathVariable("name") String name){

        List category = categoryService.getproductsByCategoryName(name);
        /*
        checks whether category is empty
         */

        if (category.isEmpty())throw new ClientSideException(Messages.EMPTY_CATEGORY.getMessage());
        return category;

    }
    /*
    Delete category by category id
     */

    /**
     * Method is used to fetch all categories
     * @param categoryId, is of Long type,an auto-incremented value.
     * @return Record deleted successfully message.
     * @throws Exception, throws custom exception
     */
    //http://localhost:8080/estore/category/delete/{id}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @Secured("ROLE_ADMIN")
    @DeleteMapping(path="/delete/{id}")
    public  ResponseEntity<String>deleteCategory(@PathVariable ("id")Long categoryId) throws Exception {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<String>(Messages.DELETE_CATEGORY.getMessage(),HttpStatus.OK);

    }

    }
