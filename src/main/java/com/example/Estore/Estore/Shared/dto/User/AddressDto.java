package com.example.Estore.Estore.Shared.dto.User;

public class AddressDto {
    private long id;
    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;
    private UserDto userDetails;

    public long getId() {
        return id;
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

    public UserDto getUserDetails() {
        return userDetails;
    }

    public void setId(long id) {
        this.id = id;
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

    public void setUserDetails(UserDto userDetails) {
        this.userDetails = userDetails;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }
}

