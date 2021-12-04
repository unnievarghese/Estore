package com.example.Estore.Estore.io.Entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * This class is used to create a table in the database.
 */
@Entity
@Table(name="users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -5411692867381157663L;
    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String userId;
    @Column(nullable = false,length = 50)
    private String firstName;
    @Column(nullable = false,length = 50)
    private String lastName;
    @Column(nullable = false,length = 15)
    private String phoneNumber;
    @Column(nullable = false,length = 120,unique = true)
    private String email;
    @Column(nullable = false)
    private String encryptedPassword;
    private String emailVerificationToken;
    @Column(nullable = false)
    private Boolean emailVerificationStatus = false;

    @OneToMany(mappedBy = "userDetails",cascade = CascadeType.ALL)
    private List<AddressEntity> address;

    @OneToOne(mappedBy = "userCardDetails",cascade = CascadeType.ALL)
    private CardEntity cardDetails;

    @ManyToMany(cascade = {CascadeType.ALL},fetch = FetchType.EAGER)
    @JoinTable(name="user_roles",
            joinColumns = @JoinColumn(name="user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    /**
     * Method is used to get database id.
     * @return long
     */
    public long getId() {
        return id;
    }

    /**
     * Method is used to set database id.
     * @param id unique database id.
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Method is used to get unique id generated for user.
     * @return String.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * method is used to set UserId.
     * @param userId unique userId generated for the user.
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Method is used to get firstName.
     * @return String.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method is used to set firstName.
     * @param firstName firstname entered by user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method is used to get lastName.
     * @return String.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method is used to set lastName.
     * @param lastName lastName entered by the user.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Method is used to get phoneNumber.
     * @return String.
     */
    public String  getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Method is used to set PhoneNUmber.
     * @param phoneNumber phone number entered by user.
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Method is used to get email.
     * @return String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method is used to set email.
     * @param email email entered by user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is used to get encrypted password.
     * @return String.
     */
    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    /**
     * Method is used to set encrypted password.
     * @param encryptedPassword encrypted password entered by user.
     */
    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    /**
     * Method is used to get email verification token.
     * @return String.
     */
    public String getEmailVerificationToken() {
        return emailVerificationToken;
    }

    /**
     * Method is used to set email verification token.
     * @param emailVerificationToken email verification token.
     */
    public void setEmailVerificationToken(String emailVerificationToken) {
        this.emailVerificationToken = emailVerificationToken;
    }

    /**
     * Method is used get email verification status.
     * @return Boolean.
     */
    public Boolean getEmailVerificationStatus() {
        return emailVerificationStatus;
    }

    /**
     * Method is used to set email verification status.
     * @param emailVerificationStatus email verification status.
     */
    public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
        this.emailVerificationStatus = emailVerificationStatus;
    }

    /**
     * Method is used to get cardDetails.
     * @return CardEntity.
     */
    public CardEntity getCardDetails() {
        return cardDetails;
    }

    /**
     * Method is used to set cardDetails.
     * @param cardDetails CardDetails entered by user.
     */
    public void setCardDetails(CardEntity cardDetails) {
        this.cardDetails = cardDetails;
    }

    /**
     * Method is used to get address.
     * @return List<AddressEntity>
     */
    public List<AddressEntity> getAddress() {
        return address;
    }

    /**
     * Method is used to set address.
     * @param address List of addresses entered by user.
     */
    public void setAddress(List<AddressEntity> address) {
        this.address = address;
    }

    /**
     * Method is used to get roles.
     * @return Collection<RoleEntity>
     */
    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    /**
     * Method is used to set roles.
     * @param roles roles given to user.
     */
    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
