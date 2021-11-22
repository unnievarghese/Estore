package com.example.Estore.Estore.io.Repositories.Product;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends CrudRepository<ProductEntity,Long> {

    ProductEntity findByProductId(Long productId);
    ProductEntity getById(Long productId);

}
