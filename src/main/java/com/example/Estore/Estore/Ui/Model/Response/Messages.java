package com.example.Estore.Estore.Ui.Model.Response;

/**
 * This Enum is used to store all the error messages used in the project.
 */
public enum Messages {

    MISSING_REQUIRED_FIELD("Required fields missing"),
    RECORD_ALREADY_EXISTS("Verified account already exists for this email, please use a different email."),
    NO_RECORD_FOUND("Account with provided id not found"),
    TOKEN_NOT_FOUND("Given token is corrupted"),
    EMAIL_TOKEN_EXPIRED("Token expired! Try to generate a new email verification link."),
    DELETE_SUCCESS("Account deleted successfully."),
    DEACTIVATE_SUCCESS("Account Deactivated successfully."),
    ACTIVATE_SUCCESS("Account activated successfully."),
    COULD_NOT_ACTIVATE("Account activation failed."),
    COULD_NOT_DEACTIVATE_RECORD("Account Deactivation failed."),
    COULD_NOT_DELETE_RECORD("Could not delete account"),
    EMAIL_NOT_FOUND("Account with given Email not found,Please check email again!"),
    EMAIL_SENT("Email successfully sent,Please check your email account."),
    EMAIL_NOT_SENT("Email not sent."),
    EMAIL_ADDRESS_NOT_VERIFIED("Email address could not be verified"),
    EMAIL_ADDRESS_VERIFIED("Email Authentication Successfull"),
    EMAIL_VERIFICATION_INCOMPLETE("Please log in to your email to complete registration."),
    PASSWORD_NOT_MATCHING("Entered passwords are different!"),
    PASSWORD_NOT_CHANGED("Password could not be changed."),
    PASSWORD_TOKEN_EXPIRED("Token expired! Try to generate new passwword reset link."),
    NO_ACCESS("You have no access to this account."),
    RESEND_EMAIL("Account already created for this email.Please complete email verification,or generate a new " +
            "email verification link."),
    FAILED_DB_SAVE("Saving to Database failed! please try again."),
    PASSWORD_CHANGED("Password Successfully changed."),

    EMPTY_CART("There are no products in the cart"),
    INVALID_PRODUCTID("No product with this ID added to cart"),
    NO_ORDER("No order history with the user"),
    NO_ORDER_STATUS("No orders with the given status"),
    INVALID_STATUS("This is not a valid status. Status can be shipped , in-transit , delivered or cancelled"),
    SAME_STATUS("The order is already with the same status"),
    ALREADY_CANCELLED("The order is already cancelled"),
    CANCEL_REJECTED("You cannot cancel the order as it is in-transit / delivered."),
    USER_CANCELLED("User Cancelled the order"),
    INVALID_ORDERID("There is no order with this ID"),

    NO_ADDRESS("Both Billing and Shipping address must be set"),


    INVALID_INPUT("the given input is invalid."),
    PRODUCT_ID_NOT_FOUND("productId not provided"),
    PRODUCT_DOES_NOT_EXIST("Product with given id does not exist,Please verify the id again"),
    EMPTY_RECORD("the record is empty");



    private String Message;

    /**
     * This constructor is used to call an enum with particular error name requested.
     * @param Message Name of the error requested.
     */
    Messages(String Message) {
        this.Message = Message;
    }

    /**
     * This method is used to get the enum with the error name.
     * @return String.
     */
    public String getMessage() {
        return Message;
    }

    /**
     * This method is used to set the enum with the error name.
     * @param Message New name for the enum.
     */
    public void setMessage(String Message) {
        this.Message = Message;
    }
}
