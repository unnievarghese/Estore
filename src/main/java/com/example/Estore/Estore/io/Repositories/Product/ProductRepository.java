package com.example.Estore.Estore.io.Repositories.Product;

import com.example.Estore.Estore.Shared.dto.Product.ProductDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Long> {


}
