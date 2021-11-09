package com.example.Estore.Estore.io.Repositories;

import com.example.Estore.Estore.io.Entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    CategoryEntity findByName(String name);
}
