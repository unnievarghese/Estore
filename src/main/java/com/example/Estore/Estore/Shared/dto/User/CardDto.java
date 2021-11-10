package com.example.Estore.Estore.Shared.dto.User;

import java.util.Date;

public class CardDto {
    private Long id;
    private String cardId;
    private Long cardNumber;
    private Long cvv;
    private Date expiryDate;
    private UserDto userCardDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Long getCvv() {
        return cvv;
    }

    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public UserDto getUserCardDetails() {
        return userCardDetails;
    }

    public void setUserCardDetails(UserDto userCardDetails) {
        this.userCardDetails = userCardDetails;
    }
}
