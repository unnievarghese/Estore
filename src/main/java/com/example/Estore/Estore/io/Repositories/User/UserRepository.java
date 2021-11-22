package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
    UserEntity findByEmailVerificationToken(String token);

    @Modifying
    @Query(value = "DELETE FROM addresses WHERE user_id = :id",nativeQuery = true)
    void deleteAddress(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM card_details WHERE user_id = :id",nativeQuery = true)
    void deleteCard(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE user_id = :id",nativeQuery = true)
    void deleteRole(@Param("id") Long id);

    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :id",nativeQuery = true)
    int deleteUser(@Param("id") Long id);
}