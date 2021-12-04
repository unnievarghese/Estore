package com.example.Estore.Estore.Exception;

/**
 * This class extends runtime exceptions so that it could be intercepted with custom exceptions.
 */
public class ClientSideException extends RuntimeException{

    /**
     * Method gets the error message to be displayed in response json
     * @param message error message to be displayed.
     */
    public ClientSideException(String message) {
        super(message);
    }
}
