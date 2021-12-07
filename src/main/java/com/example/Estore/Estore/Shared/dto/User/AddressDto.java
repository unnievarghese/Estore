package com.example.Estore.Estore.Shared.dto.User;

/**
 * This class is used to transfer address details from controller to service layer.
 */
public class AddressDto {
    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;

    /**
     * Method is used to get database id.
     * @return long
     */
    public long getId() {
        return id;
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
    public UserDto getUserDetails() {
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
    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * Method is used to get addressId.
     * @return String.
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * Method is used to set addressId.
     * @param addressId unique addressId generated for this address.
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}

