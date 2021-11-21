package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
    RoleEntity findByName(String name);
}
