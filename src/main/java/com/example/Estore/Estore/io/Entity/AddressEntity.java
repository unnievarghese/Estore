package com.example.Estore.Estore.io.Entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="addresses")
public class AddressEntity implements Serializable {

    private static final long serialVersionUID = -8977913737688794142L;
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 30,nullable = false)
    private String addressId;

    @Column(length = 15,nullable = false)
    private String city;

    @Column(length = 15,nullable = false)
    private String country;

    @Column(length = 100,nullable = false)
    private String streetName;

    @Column(length = 7,nullable = false)
    private String postalCode;

    @Column(length = 10,nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userDetails;

    public long getId() {
        return id;
    }

    public String getAddressId() {
        return addressId;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public String getStreetName() {
        return streetName;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getType() {
        return type;
    }

    public UserEntity getUserDetails() {
        return userDetails;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

}

