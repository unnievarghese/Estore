package com.example.Estore.Estore.io.Repositories.Order;

import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    //String deleteByOrderId(String orderId);
    OrderEntity findbyOrderAmount(long orderAmount);


    Optional<OrderEntity> findByOrderId(Long orderId);

    void deleteByOrderId(String orderId);
}
