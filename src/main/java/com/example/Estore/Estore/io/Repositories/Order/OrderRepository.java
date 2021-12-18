package com.example.Estore.Estore.io.Repositories.Order;

import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByorderId(Long orderId);


    @Query(value="SELECT * FROM orders cs where cs.user_id=?1",nativeQuery = true)
    List<OrderEntity> findByuserId(Long userId);

    @Query(value="SELECT * FROM orders cs where cs.user_id=?1 and cs.order_id=?2",nativeQuery = true)
    OrderEntity findByUserIdandOrderId(Long userId,Long orderId);

    @Query(value = "SELECT * FROM orders cs where cs.user_id=?1 and cs.order_status=?2",nativeQuery = true)
    List<OrderEntity> findByOrderStatusandUserId(Long userId, String orderStatus);

    @Query(value = "SELECT * FROM orders cs where cs.seller_id=?1 and cs.order_id=?2",nativeQuery = true)
    OrderEntity findBySellerIdandOrderId(Long userId, Long orderid);

    
    
}
