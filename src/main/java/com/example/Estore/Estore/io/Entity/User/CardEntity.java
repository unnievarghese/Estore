package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * This class is used to create a table in the database.
 */
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
    public long getCvv() {
        return cvv;
    }

    /**
     * Method tp set cvv.
     * @param cvv cvv entered by user.
     */
    public void setCvv(long cvv) {
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
     * Method to get cardDetails.
     * @return UserDto.
     */
    public UserEntity getUserCardDetails() {
        return userCardDetails;
    }

    /**
     * Method to set cardDetails.
     * @param userCardDetails points to the user to whom address belong.
     */
    public void setUserCardDetails(UserEntity userCardDetails) {
        this.userCardDetails = userCardDetails;
    }
}
