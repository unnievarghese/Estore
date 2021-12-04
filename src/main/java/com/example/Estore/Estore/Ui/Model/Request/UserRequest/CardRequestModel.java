package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

import java.util.Date;

/**
 * This class is used to get card details entered by user.
 */
public class CardRequestModel {

    private Long cardNumber;
    private Long cvv;
    private Date expiryDate;

    /**
     * Method is used to get cardNumber.
     * @return Long.
     */
    public Long getCardNumber() {
        return cardNumber;
    }

    /**
     * Method is used to set cardNumber.
     * @param cardNumber CardNumber entered by user.
     */
    public void setCardNumber(Long cardNumber) {
        this.cardNumber = cardNumber;
    }

    /**
     * Method is used to get cvv.
     * @return String.
     */
    public Long getCvv() {
        return cvv;
    }

    /**
     * Method is used to set cvv.
     * @param cvv Cvv entered by user.
     */
    public void setCvv(Long cvv) {
        this.cvv = cvv;
    }

    /**
     * Method is used to get expiryDate.
     * @return Date.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    /**
     * Method is used to set CardExpiryDate.
     * @param expiryDate expiry date entered by user.
     */
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
