package com.example.Estore.Estore.Ui.Model.Response;

import java.util.List;

public class UserRest {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private List<AddressesRest> address;
    private CardRest card;

    public String getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<AddressesRest> getAddress() {
        return address;
    }

    public void setAddress(List<AddressesRest> address) {
        this.address = address;
    }

    public CardRest getCard() {
        return card;
    }

    public void setCard(CardRest card) {
        this.card = card;
    }
}
