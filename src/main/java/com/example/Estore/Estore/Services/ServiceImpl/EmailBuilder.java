package com.example.Estore.Estore.Services.ServiceImpl;

/**
 * Class is used to build the body of the email to be sent.
 */
public class EmailBuilder {

    /**
     * Method is used to get the content of registration email.
     * @param name name of the user.
     * @param link Account activating link.
     * @return String.
     */
    public String buildRegistrationContent(String name,String link) {
        return "Dear "+name+",<br>"
                + "Please click the link below to verify your registration:<br>"
                + "<h3><a href="+link+" target=\"_self\">VERIFY</a></h3>"
                + "Thank you.<br>";
    }

    /**
     * Method is used to get the content of password reset email.
     * @param name name of the user.
     * @param link password reset link.
     * @return String.
     */
    public String buildPasswordResetContent(String name,String link) {
        return "Dear "+name+",<br>"
                + "Please click the link below to change password:<br>"
                + "<h3><a href="+link+" target=\"_self\">VERIFY</a></h3>"
                + "Thank you.<br>";
    }

    /**
     * Method is used to get the content of password reset email.
     * @param name name of the user.
     * @param link password reset link.
     * @return String.
     */
    public String buildUserActivateContent(String name,String link) {
        return "Dear "+name+",<br>"
                + "Please click the link below to activate account:<br>"
                + "<h3><a href="+link+" target=\"_self\">ACTIVATE</a></h3>"
                + "Thank you.<br>";
    }
}
