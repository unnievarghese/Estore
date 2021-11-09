package com.example.Estore.Estore.Ui.Model.Request;

import com.example.Estore.Estore.io.Entity.CardEntity;
import java.util.List;

public class UserDetailsRequestModel {

    private String firstName;

    private String lastName;

    private Long phoneNumber;

    private String email;

    private String password;

    private CardRequestModel cardDetails;

    private List<AddressRequestModel> address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CardRequestModel getCardDetails() {
        return cardDetails;
    }

    public void setCardDetails(CardRequestModel cardDetails) {
        this.cardDetails = cardDetails;
    }

    public List<AddressRequestModel> getAddress() {
        return address;
    }

    public void setAddress(List<AddressRequestModel> address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
