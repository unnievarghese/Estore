package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

/**
 * Class is used to get the new password entered by the user.
 */
public class PasswordResetModel {

    private String newPassword;
    private String repeatPassword;

    /**
     * Method is used to get new password.
     * @return String.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * Method is used to set New password.
     * @param newPassword new password entered by user.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * Method is used to get repeated password.
     * @return String.
     */
    public String getRepeatPassword() {
        return repeatPassword;
    }

    /**
     * Method is used to set repeated password.
     * @param repeatPassword repeated password entered by user.
     */
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
