package com.example.Estore.Estore.io.Repositories.User;

import com.example.Estore.Estore.io.Entity.User.AddressEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * This interface is used to access the database.
 */
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity , Long> {

    /**
     * Method is used to find address using addressId.
     * @param addressId unique addressId generated for each address.
     * @return AddressEntity.
     */
    AddressEntity findByAddressId(String addressId);
}
