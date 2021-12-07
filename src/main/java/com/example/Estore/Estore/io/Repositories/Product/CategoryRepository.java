package com.example.Estore.Estore.io.Repositories.Product;

import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface CategoryRepository extends PagingAndSortingRepository<CategoryEntity, Long> {
    CategoryEntity findByCategoryName(String categoryName);
    CategoryEntity findByCategoryId(Long categoryId);
    CategoryEntity getById(Long categoryId);

}
