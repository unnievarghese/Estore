package com.example.Estore.Estore.io.Repositories.WishList;

import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface WishListRepository extends JpaRepository<WishListEntity,Long> {
    @Query(value = "select * from wishlist w where w.user_id=?1",nativeQuery = true)
    Optional<WishListEntity>findByUserId(String userId);
    @Query(value = "select * from wishlist w where w.user_id=?1",nativeQuery = true)
    WishListEntity getByUserId(String userId);

    @Transactional
    @Modifying
    @Query(value = "delete from wish list_products w where w.wishlist_id=?1 and w.product_id=?2",nativeQuery = true)
    void deleteProduct(Long wishListId,Long productId);



}
