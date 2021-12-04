package com.example.Estore.Estore.Ui.Model.Response.UserRequest;

/**
 * This class is called to send a response to client, after creating a user.
 */
public class AddressesRest{

    private String addressId;
    private String city;
    private String country;
    private String streetName;
    private String postalCode;
    private String type;

    /**
     * Method to get addressId.
     * @return String
     */
    public String getAddressId() {
        return addressId;
    }

    /**
     * Method the set addressId.
     * @param addressId unique addressId generated for each address.
     */
    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    /**
     * Method to get city.
     * @return String.
     */
    public String getCity() {
        return city;
    }

    /**
     * Method to set city.
     * @param city City name entered by the user.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Method to get country name.
     * @return sting.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Method to set country name.
     * @param country Country name entered by user.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Method to get street name.
     * @return String.
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Method to set street name.
     * @param streetName street name entered by user.
     */
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    /**
     * Method to get postalCode.
     * @return Sting.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * Method is set postalCode.
     * @param postalCode postalCode entered by user.
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * Method is to get address type.
     * @return String
     */
    public String getType() {
        return type;
    }

    /**
     * Method to set the UserType.
     * @param type type entered by the user.
     */
    public void setType(String type) {
        this.type = type;
    }
}
