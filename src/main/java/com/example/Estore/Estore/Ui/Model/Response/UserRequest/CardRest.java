package com.example.Estore.Estore.Ui.Model.Response.UserRequest;

import java.util.Date;

/**
 * This class is called to send a response to client, after creating a user.
 */
public class CardRest {

    private Long cardNumber;
    private Long cvv;
    private Date expiryDate;

    /**
     * Method is used to get cardNumber.
     * @return Long
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * Method is used to set CardNumber.
     * @param cardNumber cardNumber entered by the user.
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Method is used to get cvv.
     * @return Long
     */
    public Long getCvv() {
        return cvv;
    }

    /**
     * Method is used to set cvv.
     * @param cvv cvv entered by the user.
     */
    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    /**
     * Method is used to set ExpiryDate.
     * @return Date.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Method is used to set ExpiryDate.
     * @param expiryDate expiryDate entered by the user.
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
