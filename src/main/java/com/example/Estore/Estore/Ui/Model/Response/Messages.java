package com.example.Estore.Estore.Ui.Model.Response;

public enum Messages {

    MISSING_REQUIRED_FIELD("Required fields missing"),
    RECORD_ALREADY_EXISTS("Record already exists"),
    NO_RECORD_FOUND("Record with provided id not found"),
    TOKEN_NOT_FOUND("Given token is corrupted"),
    TOKEN_EXPIRED("Token expired!"),
    DELETE_SUCCESS("Record deleted successfully."),
    COULD_NOT_DELETE_RECORD("Could not delete record"),
    EMAIL_NOT_FOUND("Account with given Email not found,Please check email again!"),
    EMAIL_SENT("Email successfully sent,Please check your email account."),
    EMAIL_NOT_SENT("Email not sent."),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    EMAIL_ADDRESS_VERIFIED("Email Authentication Successfull"),
    PASSWORD_NOT_MATCHING("Entered passwords are different!"),
    PASSWORD_NOT_CHANGED("Password could not be changed."),
    PASSWORD_CHANGED("Password Successfully changed."),
    OUT_OF_STOCK("required quantity not available,Please wait for the seller to add products"),
    PRODUCT_DOES_NOT_EXIST("Product with given id does not Exists,Please verify the id again!"),
    CART_NOT_ACTIVE("Cart is not active,Please verify id again!"),
    PRODUCT_ID_NOT_FOUND("productId not provided"),
    CART_IS_EMPTY("Your cart is empty, Please add products!");


    private String Message;

    Messages(String Message) {
        this.Message = Message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;
    }
}
