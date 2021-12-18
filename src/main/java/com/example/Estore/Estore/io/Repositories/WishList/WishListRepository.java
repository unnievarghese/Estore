package com.example.Estore.Estore.io.Repositories.WishList;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishListRepository extends CrudRepository<WishListEntity, Long> {


    Optional<WishListEntity> findAllByUserEntity(UserEntity userEntity);

    WishListEntity findByUserEntity(UserEntity userEntity);

    WishListEntity findAllByWishListId(Long wishListId);


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM wishlist_product_entity_list w WHERE w.wish_list_entity_wish_list_id=?1 AND w.product_entity_list_product_id=?2",nativeQuery = true)
    void deleteProduct(Long wishListId, Long productId);


//    @Query(value="SELECT *  FROM wishlist_product_entity_list w WHERE w.wish_list_entity_wish_list_id=?1 AND w.product_entity_list_product_id=?2",nativeQuery = true)
@Query(value = "SELECT * FROM wishlist_product_entity_list up WHERE up.product_entity_list_product_id=?1 AND wish_list_entity_wish_list_id=?2", nativeQuery = true)
WishListEntity findByProductIdAndWishlistId(Long productId,Long wishlistId);

}
