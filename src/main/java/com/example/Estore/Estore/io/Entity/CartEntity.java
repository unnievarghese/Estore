package com.example.Estore.Estore.io.Entity;

import jdk.jfr.Enabled;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Cart")
public class CartEntity implements Serializable {

    private static final long serialVersionUID = -2740191349965477116L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String cartId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userCartDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public UserEntity getUserCartDetails() {
        return userCartDetails;
    }

    public void setUserCartDetails(UserEntity userCartDetails) {
        this.userCartDetails = userCartDetails;
    }
}
