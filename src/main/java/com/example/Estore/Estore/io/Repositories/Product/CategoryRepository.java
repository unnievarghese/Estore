package com.example.Estore.Estore.io.Repositories.Product;

import com.example.Estore.Estore.io.Entity.Product.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findByCategoryName(String categoryName);

}
