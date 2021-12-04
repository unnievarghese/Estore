package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class is used to create a table in the database.
 */
@Entity(name="Password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    private static final long serialVersionUID= -2283966103318060186L;
    @Id
    @GeneratedValue
    private long id;
    private String token;

    @OneToOne
    @JoinColumn(name="users_id")
    private UserEntity userDetails;

    /**
     * Method is used to get database id.
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method is used to get jwt token generated for password reset.
     * @return String.
     */
    public String getToken() {
        return token;
    }

    /**
     * Method is used to set jwt token for password reset.
     * @param token Jwt token.
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Method is used to get userDetails.
     * @return UserEntity
     */
    public UserEntity getUserDetails() {
        return userDetails;
    }

    /**
     * Method is used to set userDetails.
     * @param userDetails UserEntity.
     */
    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }
}

