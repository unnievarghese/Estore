package com.example.Estore.Estore.Ui.Model.Request.UserRequest;

public class PasswordResetModel {

    private String newPassword;
    private String repeatPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
