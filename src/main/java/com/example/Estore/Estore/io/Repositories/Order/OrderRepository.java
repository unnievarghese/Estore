package com.example.Estore.Estore.io.Repositories.Order;

import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByorderId(Long orderId);




//    OrderEntity findByid(Long orderId);
//
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM orders ci WHERE ci.order_id=?1",nativeQuery = true)
    void deleteOrder(Long id);

//    @Query(value="SELECT * FROM orders cs where cs.user_id=?1",nativeQuery = true)
//    List<OrderEntity> findByuserId(UserEntity userEntity);

    @Query(value="SELECT * FROM orders cs where cs.user_id=?1",nativeQuery = true)
    List<OrderEntity> findByuserId(Long userId);

//    @Query(value="SELECT * FROM orders cs where cs.user_id=?1",nativeQuery = true)
//    Optional<OrderEntity> findByuserId(Long userId);

    // @Query(value = "SELECT * FROM orders cs where cs.user_id=?1",nativeQuery = true)
    //List<OrderEntity> findByUserId(UserEntity userEntity);
    
    
}
