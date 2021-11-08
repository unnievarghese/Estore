package com.example.Estore.Estore.Ui.Model.Request;

import javax.persistence.Column;
import java.util.Date;

public class CardRequestModel {

    private Long cardNumber;
    private Long cvv;
    private Date expiryDate;

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
}
