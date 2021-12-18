package com.example.Estore.Estore.io.Repositories.Cart;

import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface CartItemRepository extends JpaRepository<CartItemEntity, Long> {
    /**
     * Method for finding User from repository
     * @param userEntity id of user
     * @return CartItemEntity
     */
    List<CartItemEntity> findByUserEntity(UserEntity userEntity);

    @Transactional
    @Modifying

    /**
     * Query for finding product of respective users from repository for deletion which is only active
     */
    @Query(value = "DELETE FROM cartitem ci WHERE ci.product_id=?1 and ci.user_id=?2 and ci.cart_is_active=true", nativeQuery = true)
    void deleteProduct(Long productId, UserEntity user);

    /**
     * Query for finding product of respective users from repository which is only active
     * @param userEntity id of user
     * @param productId  id of product
     * @return CartItemEntity
     */

    @Query(value = "SELECT * FROM cartitem up where up.product_id=?2 and up.user_id=?1 and up.cart_is_active=true", nativeQuery = true)
    CartItemEntity findByUserEntityANDProductId(UserEntity userEntity, Long productId);

    /**
     * Query for finding cart of user which is active
     * @param userEntity id of user
     * @return CartItemEntity
     */
    @Query(value = "SELECT * FROM cartitem cs where cs.cart_is_active=true and cs.user_id=?1", nativeQuery = true)
    List<CartItemEntity> findByCartStatus(UserEntity userEntity);

    /**
     * Query for finding product of user which is active
     * @param productId id of product
     * @return CartItemEntity
     */
    @Query(value = "SELECT * FROM cartitem pi where pi.cart_is_active=true and pi.product_id=?1", nativeQuery = true)
    List<CartItemEntity> findByProductId(Long productId);

    /**
     * Query for finding all active carts
     * @return CartItemEntity
     */
    @Query(value = "SELECT * FROM cartitem pi where pi.cart_is_active=true", nativeQuery = true)
    List<CartItemEntity> findByStatus();

}
