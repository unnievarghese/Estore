package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

/**
 * This class is used to get the address entered by user.
 */
public class AddressRequestModel {
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

    /**
     * Method is used to get the city name.
     * @return String.
     */
    public String getCity() {
        return city;
    }

    /**
     * Method is used to get country name.
     * @return String.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method is used to get street name.
     * @return String.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Method is used to get postalCode.
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
     * Method is used to set City name.
     * @param city City name entered by user.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method is used set name of country.
     * @param country country name entered by user.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method is used to set name of street.
     * @param streetName street name entered by user.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Method is used to set postal code.
     * @param postalCode postal code entered by user.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method is used to set type of address.
     * @param type address type entered by user.
     */
    public void setType(String type) {
        this.type = type;
    }
}