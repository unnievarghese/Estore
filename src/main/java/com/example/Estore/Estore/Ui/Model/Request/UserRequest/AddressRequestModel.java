package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

public class AddressRequestModel {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

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
}

