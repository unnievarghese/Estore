package com.example.Estore.Estore.io.Repositories.WishList;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface WishListRepository extends CrudRepository<WishListEntity, Long> {

    Optional<WishListEntity> findAllByUserEntity(UserEntity userEntity);

    WishListEntity findByUserEntity(UserEntity userEntity);

    /**
     * Method to delete product from the wishlist.
     * @param wishListId
     * @param productId
     */
    @Transactional
    @Modifying

    @Query(value = "DELETE FROM wishlist_products w WHERE w.wish_list_id=?1 AND w.product_id=?2",nativeQuery = true)
    void deleteProduct(Long wishListId, Long productId);

    @Query(value = "SELECT * FROM wishlist w WHERE w.user_reference_id=?1", nativeQuery = true)
    WishListEntity findByUserId(long id);
}