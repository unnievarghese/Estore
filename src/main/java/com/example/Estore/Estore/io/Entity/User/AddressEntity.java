package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * This class is used to create a table in the database.
 */
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

    /**
     * Method is used to get database id.
     * @return long
     */
    public long getId() {
        return id;
    }

    public String getAddressId() {
        return addressId;
    }

    /**
     * Method is used to get name of city.
     * @return String.
     */
    public String getCity() {
        return city;
    }

    /**
     * Method is used to get name of country.
     * @return String.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method is used to get name of street.
     * @return String.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Method is used to get postal code.
     * @return String.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method is used to get type of address.
     * @return String.
     */
    public String getType() {
        return type;
    }

    /**
     * Method is used to get the user details.
     * @return UserDto.
     */
    public UserEntity getUserDetails() {
        return userDetails;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method is used to set addressId.
     * @param addressId unique addressId generated for this address.
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * Method is used to set name of city.
     * @param city name of city.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method is used to set name of country.
     * @param country name of country.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method is used to set name of street.
     * @param streetName name of the street.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Method is used to set postal code.
     * @param postalCode postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method is used to set type of address.
     * @param type type of address.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method is used to set userDetails.
     * @param userDetails points to the user to whom address belong.
     */
    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

}

