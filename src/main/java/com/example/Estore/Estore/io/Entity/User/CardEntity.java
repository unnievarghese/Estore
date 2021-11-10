package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "cardDetails")
public class CardEntity implements Serializable {

    private static final long serialVersionUID = -5271438957437210217L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(length = 25)
    private Long cardNumber;

    @Column(length = 5)
    private Long cvv;

    @Column(length = 15)
    private Date expiryDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserEntity userCardDetails;

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

    public long getCvv() {
        return cvv;
    }

    public void setCvv(long cvv) {
        this.cvv = cvv;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserEntity getUserCardDetails() {
        return userCardDetails;
    }

    public void setUserCardDetails(UserEntity userCardDetails) {
        this.userCardDetails = userCardDetails;
    }
}
