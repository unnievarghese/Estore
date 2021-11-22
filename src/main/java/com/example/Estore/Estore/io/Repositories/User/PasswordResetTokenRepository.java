package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity,Long> {

    PasswordResetTokenEntity findByToken(String token);
}
