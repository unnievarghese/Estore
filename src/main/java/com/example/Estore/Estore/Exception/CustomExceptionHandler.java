package com.example.Estore.Estore.Exception;

import com.example.Estore.Estore.Ui.Model.Response.Message;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import java.util.Date;

/**
 * This class handles custom exceptions across the whole application.
 */
@ControllerAdvice
public class CustomExceptionHandler {

    /**
     * Method is used to throw custom exceptions for error rising form client's side.
     * @param ex ClientSideException
     * @param request WebRequest
     * @return ResponseEntity<Object>
     */
    @ExceptionHandler(value = {ClientSideException.class})
    public ResponseEntity<Object> handleUserServiceException(ClientSideException ex, WebRequest request){
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        Message message = new Message(new Date(), ex.getMessage(),httpStatus);

        return new ResponseEntity<>(message, new HttpHeaders(), httpStatus);
    }
}
