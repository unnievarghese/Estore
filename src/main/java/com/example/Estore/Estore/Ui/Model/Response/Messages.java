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
    CATEGORY_NOT_FOUND("Provided category not found. Please wait admin to add category"),
    EMPTY_CATEGORY("No products found in the given category"),
    PRODUCT_ALREADY_EXISTS("Product already exists. Please update the product quantity");

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
