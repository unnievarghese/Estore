package com.example.Estore.Estore.Ui.Model.Response.UserRequest;

import java.util.List;

/**
 * This class is called to send a response to client, after creating a user.
 */
public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressesRest> address;
    private CardRest card;

    /**
     * Method is used to get userId
     * @return String
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Method is used to get firstName
     * @return String
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method is used to get lastName
     * @return String
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method is used to get Email
     * @return String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method is used to set UserId
     * @param userId unique userId generated of each user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Method is used to set FirstName.
     * @param firstName user's firstName.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method is used to set lastName.
     * @param lastName user's lastName.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method is used to set email.
     * @param email user's email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is used to get address.
     * @return List<AddressesRest>
     */
    public List<AddressesRest> getAddress() {
        return address;
    }

    /**
     * Method is used to set address.
     * @param address list of addresses entered by the user.
     */
    public void setAddress(List<AddressesRest> address) {
        this.address = address;
    }

    /**
     * Method is used get card details.
     * @return CardRest
     */
    public CardRest getCard() {
        return card;
    }

    /**
     * Method used to set card details.
     * @param card card details entered by the user.
     */
    public void setCard(CardRest card) {
        this.card = card;
    }
}
