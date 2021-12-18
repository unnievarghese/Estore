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
    PASSWORD_CHANGED("Password Successfully changed."),
    PASSWORD_TOKEN_EXPIRED("Token expired! Try to generate new passwword reset link."),
    NO_ACCESS("You have no access to this account."),
    RESEND_EMAIL("Account already created for this email.Please complete email verification,or generate a new " +
            "email verification link."),
    FAILED_DB_SAVE("Saving to Database failed! please try again."),
    ACCOUNT_ACTIVE("Account already active"),
    EMPTY_CART("There are no products in the cart"),
    INVALID_PRODUCTID("No product with this ID added to cart"),
    NO_ORDER("No order history with the user"),
    NO_ORDER_STATUS("No orders with the given status"),
    INVALID_STATUS("This is not a valid status. Status can be placed,confirmed,shipped,delivered,usercancelled or sellercancelled"),
    INVALID_STATUS1("This is not a valid status. Status can be confirmed,shipped,delivered or sellercancelled"),
    SAME_STATUS("The order is already with the same status"),
    ALREADY_CANCELLED1("The order is already cancelled by you"),
    ALREADY_CANCELLED2("The order is already cancelled by seller"),
    CANCEL_REJECTED("Can't update now."),
    CANCEL_REJECTED1("You cannot cancel as the order is on the way"),
    CANCEL_REJECTED2("Order already delivered."),
    USER_CANCELLED("User Cancelled the order"),
    INVALID_ORDERID("There is no order with this ID"),
    NOT_DELIVERED("Order not delivered yet"),
    ORDER_CANCELLED("Order is cancelled"),
    ALREADY_RETURNED("Order already returned "),
    CANT_RETURN("7 days over, can't return now "),
    NO_ADDRESS("Both Billing and Shipping address must be set"),
    SET_CARD("Card details must be set"),
    INVALID_INPUT("the given input is invalid."),
    PRODUCT_ID_NOT_FOUND("productId not provided"),
    PRODUCT_DOES_NOT_EXIST("Product with given id does not exist,Please verify the id again"),
    EMPTY_RECORD("the record is empty"),
    PRODUCT_ALREADY_EXISTS("product already exits."),
    DELETE_PRODUCT("product deleted successfully"),
    DELETE_REVIEW("review deleted successfully"),
    REVIEW_NOT_FOUND("now review found for this productid"),
    OUT_OF_STOCK("required quantity not available,Please wait for the seller to add products"),
    CART_NOT_ACTIVE("Cart is not active,Please verify id again!"),
    CART_IS_EMPTY("Your cart is empty, Please add products!"),
    CATEGORY_NOT_FOUND("Provided category not found. Please wait admin to add category"),
    EMPTY_CATEGORY("No products found in the given category");


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
