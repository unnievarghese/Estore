package com.example.Estore.Estore.Ui.Model.Response;

import org.springframework.http.HttpStatus;
import java.util.Date;

/**
 * This class is used to build the json error response sent to the user.
 */
public class Message {

    private Date timeStamp;
    private String message;
    private HttpStatus httpStatus;

    public Message(){}

    /**
     * This Constructor initializes the Message object with the provided arguments.
     * @param timeStamp Current time.
     * @param message Error message to be displayed.
     * @param httpStatus HttpStatus of the error.
     */
    public Message(Date timeStamp, String message, HttpStatus httpStatus){
        this.timeStamp = timeStamp;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    /**
     * Method used to get current time.
     * @return Date
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * Method used to set current time.
     * @param timeStamp Current time.
     */
    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    /**
     * Method used to get message.
     * @return String
     */
    public String getMessage() {
        return message;
    }

    /**
     * Method used to set message.
     * @param message The message object needed to be sent as response.
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Method used to get HttpStatus.
     * @return HttpStatus
     */
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    /**
     * Method used set HttpStatus.
     * @param httpStatus HttpStatus.
     */
    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
