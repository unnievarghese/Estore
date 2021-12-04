package com.example.Estore.Estore.Shared.dto.User;

import java.util.Date;

/**
 * This class is used to transfer card details from controller to service layer.
 */
public class CardDto {
    private Long id;
    private String cardId;
    private Long cardNumber;
    private Long cvv;
    private Date expiryDate;
    private UserDto userCardDetails;

    /**
     * Method is used to get database id.
     * @return long
     */
    public Long getId() {
        return id;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Method to get cardNUmber.
     * @return Long.
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * Method to set CardNUmber.
     * @param cardNumber cardNumber entered by user.
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Method to get cvv.
     * @return Long.
     */
    public Long getCvv() {
        return cvv;
    }

    /**
     * Method tp set cvv.
     * @param cvv cvv entered by user.
     */
    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    /**
     * Method to get ExpiryDate.
     * @return Date.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Method to set ExpiryDate.
     * @param expiryDate date entered by user
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * Method to get cardId.
     * @return String.
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Method to set cardId.
     * @param cardId unique cardId generated for this card.
     */
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    /**
     * Method to get cardDetails.
     * @return UserDto.
     */
    public UserDto getUserCardDetails() {
        return userCardDetails;
    }

    /**
     * Method to set cardDetails.
     * @param userCardDetails points to the user to whom address belong.
     */
    public void setUserCardDetails(UserDto userCardDetails) {
        this.userCardDetails = userCardDetails;
    }
}
