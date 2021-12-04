package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

/**
 * This class is used to get login credentials from the user.
 */
public class UserLoginRequestModel {
    private String email;
    private String password;

    /**
     * Method is used to get email.
     * @return String.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Method is used to set email.
     * @param email email entered by the user.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Method is to get password.
     * @return String.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Method is used to set password.
     * @param password password entered by the user.
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
