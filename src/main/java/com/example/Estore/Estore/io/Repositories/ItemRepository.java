package com.example.Estore.Estore.io.Repositories;

import com.example.Estore.Estore.io.Entity.CategoryEntity;
import com.example.Estore.Estore.io.Entity.ItemEntity;
import org.springframework.data.repository.CrudRepository;

public interface ItemRepository extends CrudRepository<ItemEntity,Long> {
    ItemEntity findByName(String name);
}
