package com.example.Estore.Estore.io.Repositories.Product;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Pageable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;


@Repository
public interface ProductRepository extends PagingAndSortingRepository<ProductEntity,Long> {


    ProductEntity findByProductId(Long productId);
    ProductEntity getById(Long productId);
    ProductEntity findByProductName(String productName);

}
