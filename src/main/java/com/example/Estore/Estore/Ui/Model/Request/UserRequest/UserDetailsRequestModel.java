package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

import java.util.List;

/**
 * This class is used to get user details form the user.
 */
public class UserDetailsRequestModel {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private CardRequestModel cardDetails;
    private List<AddressRequestModel> address;

    /**
     * Method is used to get firstName.
     * @return String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method is used to set firstName.
     * @param firstName user's firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method is user to get LastName.
     * @return String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method is used to set lastName.
     * @param lastName user's lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method is used to get PhoneNumber.
     * @return String.
     */
    public String  getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method is used to set PhoneNumber.
     * @param phoneNumber user's phoneNumber.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method is used to get email.
     * @return String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method is used to set email.
     * @param email user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is used to get cardDetails.
     * @return CardRequestModel
     */
    public CardRequestModel getCardDetails() {
        return cardDetails;
    }

    /**
     * Method is used to set cardDetails.
     * @param cardDetails user's cardDetails.
     */
    public void setCardDetails(CardRequestModel cardDetails) {
        this.cardDetails = cardDetails;
    }

    /**
     * Method is used to get list of address.
     * @return List<AddressRequestModel>
     */
    public List<AddressRequestModel> getAddress() {
        return address;
    }

    /**
     * Method is used to set list of address.
     * @param address List of address entered by user.
     */
    public void setAddress(List<AddressRequestModel> address) {
        this.address = address;
    }

    /**
     * Method is used to get password.
     * @return String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method is used to set password.
     * @param password password entered by user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
