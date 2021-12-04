package com.example.Estore.Estore.Shared.dto.User;

import java.io.Serializable;
import java.util.List;

/**
 * This class is used to transfer card details from controller to service layer.
 */
public class UserDto implements Serializable {

    private static final long serialVersionUID = 7172480222401731737L;
    private long id;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String password;
    private String encryptedPassword;
    private String emailVerificationToken;
    private Boolean emailVerificationStatus = false;
    private List<AddressDto> address;
    private CardDto cardDetails;

    /**
     * Method is used to get database id.
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Method is used to get unique id generated for user.
     * @return String.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Method is used to get firstName.
     * @return String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method is used to get lastName.
     * @return String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method is used to get email.
     * @return String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method is used to get password.
     * @return String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method is used to get encrypted password.
     * @return String.
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * Method is used to get email verification token.
     * @return String.
     */
    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    /**
     * Method is used get email verification status.
     * @return Boolean.
     */
    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * method is used to set UserId.
     * @param userId unique userId generated for the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Method is used to set firstName.
     * @param firstName firstname entered by user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method is used to set lastName.
     * @param lastName lastName entered by the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method is used to set email.
     * @param email email entered by user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is used to set password.
     * @param password password entered by user.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Method is used to set encrypted password.
     * @param encryptedPassword encrypted password entered by user.
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * Method is used to set email verification token.
     * @param emailVerificationToken email verification token.
     */
    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    /**
     * Method is used to set email verification status.
     * @param emailVerificationStatus email verification status.
     */
    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    /**
     * Method is used to get address.
     * @return List<AddressDto>
     */
    public List<AddressDto> getAddress() {
        return address;
    }

    /**
     * Method is used to set address.
     * @param address List of addresses entered by user.
     */
    public void setAddress(List<AddressDto> address) {
        this.address = address;
    }

    /**
     * Method is used to get cardDetails.
     * @return CardDto.
     */
    public CardDto getCardDetails() {
        return cardDetails;
    }

    /**
     * Method is used to set cardDetails.
     * @param cardDetails CardDetails entered by user.
     */
    public void setCardDetails(CardDto cardDetails) {
        this.cardDetails = cardDetails;
    }

    /**
     * Method is used to get phoneNumber.
     * @return String.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method is used to set PhoneNUmber.
     * @param phoneNumber phone number entered by user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

