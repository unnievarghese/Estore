package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database.
 */
@Repository
public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity,Long> {

    /**
     * Method is used to get the PasswordResetTokenEntity by its token.
     * @param token jwt token generated for password reset.
     * @return PasswordResetTokenEntity.
     */
    PasswordResetTokenEntity findByToken(String token);

    /**
     * Method is used to get PasswordResetTokenEntity with it foreign key from user.
     * @param id database id of user.
     * @return PasswordResetTokenEntity.
     */
    @Query(value = "SELECT * FROM password_reset_tokens WHERE users_id = :id",nativeQuery = true)
    PasswordResetTokenEntity findByUser(@Param("id") Long id);
}
