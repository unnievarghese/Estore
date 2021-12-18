package com.example.Estore.Estore.Services.ServiceImpl;


import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ProductRequest.CategoryRest;
import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Repositories.Product.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    /**
     * Inject CategoryRepository dependency
     */
    @Autowired
    CategoryRepository categoryRepository;
    /**
     * Method to add a new product category
     * @param categoryRequestModel contains the details of the category that is to be added.
     * @return CategoryRest
     */
    @Override
    public CategoryRest addCategory(CategoryRequestModel categoryRequestModel)  {
        CategoryEntity category = categoryRepository.findByCategoryName(categoryRequestModel.getCategoryName());
        if (category!=null) throw new ClientSideException(Messages.CATEGORY_ALREADY_EXIST.getMessage());
        CategoryEntity categoryEntity = new ModelMapper().map(categoryRequestModel,CategoryEntity.class);
        categoryRepository.save(categoryEntity);
        return new ModelMapper().map(categoryEntity,CategoryRest.class);
    }

    /**
     * Method to fetch all category of products
     * @param page, page to be indexed
     * @param limit, number of categories to be displayed in a page
     * @return CategoryEntity List
     */
    @Override
    public List<CategoryEntity> getCategory(int page, int limit)
    {
        List<CategoryEntity> categoryEntityList = new ArrayList<>();
        Pageable pageable = PageRequest.of(page, limit);
        Page<CategoryEntity> categoryEntityPage =categoryRepository.findAll(pageable);
        List<CategoryEntity> category = categoryEntityPage.getContent();
        for (CategoryEntity  categoryEntity: category){
            CategoryEntity categoryEntity1 = new ModelMapper().map(categoryEntity,CategoryEntity.class);
            categoryEntityList.add(categoryEntity1);
        }
         if (category.isEmpty()) throw new ClientSideException(Messages.CATEGORY_IS_EMPTY.getMessage());

        return categoryEntityList;

    }

    /**
     * Method to fetch products belonging to a categoryName
     * @param name, contains categoryName of products
     * @return ProductEntity List
     */
    @Override
    public List<ProductEntity> getproductsByCategoryName(String name) {
        List<CategoryEntity> category = (List<CategoryEntity>) categoryRepository.findAll();
        List<ProductEntity> productEntity = new ArrayList<>();
        /*
        if name equals the category name product details are added to productEntity
         */
        for (CategoryEntity i : category) {
            if (i.getCategoryName().equals(name)) {
                for (ProductEntity product : i.getProducts()) {
                    productEntity.add(product);
                }
            }
        }
        return productEntity;
    }

    /**
     * Method to delete a category when a categoryId is passed
     * @param categoryId, contains the unique Long id auto-generated for each category
     * @throws Exception, throws custom exception
     */
    @Override
    public void deleteCategory(Long categoryId) throws Exception {

        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
        if(categoryEntity==null) throw new ClientSideException(Messages.CATEGORY_DOES_NOT_EXIST.getMessage());
        if (categoryRepository.getById(categoryId).getCategoryId().equals(categoryId))
        {
        categoryRepository.deleteById(categoryId);}
    }

}

