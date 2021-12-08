package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * This interface is used to access the database.
 */
@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {

    /**
     * Method is used to fetch a user by its email.
     * @param email email.
     * @return UserEntity.
     */
    UserEntity findByEmail(String email);

    /**
     * Method is used to fetch a user by its userId.
     * @param userId unique userId generated foe each user.
     * @return UserEntity.
     */
    UserEntity findByUserId(String userId);

    /**
     * Method is used to fetch a user by its userId.
     * @param token jwt token generated for email verification.
     * @return UserEntity.
     */
    UserEntity findByEmailVerificationToken(String token);

    /**
     * Method is used to delete address by its foreign key from user.
     * @param id UserEntity
     */
    @Modifying
    @Query(value = "DELETE FROM addresses WHERE user_id = :id",nativeQuery = true)
    void deleteAddress(@Param("id") UserEntity id);

    /**
     * Method is used to delete card by its foreign key from user.
     * @param id UserEntity
     */
    @Modifying
    @Query(value = "DELETE FROM card_details WHERE user_id = :id",nativeQuery = true)
    void deleteCard(@Param("id") UserEntity id);

    /**
     * Method is used to delete role by its foreign key from user.
     * @param id UserEntity
     */
    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = :id",nativeQuery = true)
    void deleteRole(@Param("id") UserEntity id);

    @Modifying
    @Query(value = "DELETE FROM wishlist WHERE user_reference_id = :id",nativeQuery = true)
    void deleteWishList(@Param("id") UserEntity userEntity);

    @Modifying
    @Query(value = "DELETE FROM wishlist_product_entity_list WHERE wish_list_entity_wish_list_id = :id",nativeQuery = true)
    void deleteWishListProductList(@Param("id") WishListEntity wishListEntity);
    /**
     * Method is used to delete user by database id.
     * @param id UserEntity
     */
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :id",nativeQuery = true)
    int deleteUser(@Param("id") UserEntity id);

    /**
     * Method used to update the email verification token to true.
     * @param userId unique userId generated foe each user.
     * @return integer.
     */
    @Modifying
    @Query(value = "UPDATE users set email_verification_status = false WHERE user_id = :id",nativeQuery = true)
    int deactivateUser(@Param("id") String userId);

    /**
     * Method used to update the email verification token to true.
     * @param userId unique userId generated foe each user.
     * @return integer.
     */
    @Modifying
    @Query(value = "UPDATE users set email_verification_status = true WHERE user_id = :id",nativeQuery = true)
    int activateUser(@Param("id") String userId);
}