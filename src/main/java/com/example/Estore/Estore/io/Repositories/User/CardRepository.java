package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.CardEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database.
 */
@Repository
public interface CardRepository extends CrudRepository<CardEntity,Long> {
}
