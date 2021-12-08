package com.example.Estore.Estore.Services;

/**
 * This interface is autowired into userServiceImpl class and is used to call email service methods.
 */
public interface EmailService {

    /**
     * Method is used to send email.
     * @param to Email id provided by the user.
     * @param email The body of the email.
     */
    boolean send(String to,String email);
}
