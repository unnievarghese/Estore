package com.example.Estore.Estore.Services.ServiceImpl;


import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.CategoryService;
import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
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
    @Autowired
    CategoryRepository categoryRepository;


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
        return categoryEntityList;
//        return (List<CategoryEntity>) categoryRepository.findAll();
    }

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

    @Override
    public void deleteCategory(Long categoryId) throws Exception {

        CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId);
        if(categoryEntity==null) throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());
        if (categoryRepository.getById(categoryId).getCategoryId().equals(categoryId))
        {
        categoryRepository.deleteById(categoryId);}
    }

}

