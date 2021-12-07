package com.example.Estore.Estore.Exception;

import com.example.Estore.Estore.Ui.Model.Request.ProductRequest.ProductRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserDetailsRequestModel;

/**
 * Class is used to validate incoming json.
 */
public class Validation {

    /**
     * Method is used to check if the fields are empty.
     * @param userDetails UserDetailsRequestModel
     * @return Boolean
     */
    public Boolean checkFields(UserDetailsRequestModel userDetails){

        if(userDetails.getFirstName().isEmpty()) return false;
        if(userDetails.getLastName().isEmpty()) return false;
        if(userDetails.getEmail().isEmpty()) return false;
        if(userDetails.getPassword().isEmpty()) return false;
        if(userDetails.getPhoneNumber().isEmpty()) return false;
        if(userDetails.getAddress().isEmpty()) return false;

        return true;
    }
    /*
    to be deleted
     */
    public Boolean checkFields(ProductRequestModel productDetails){
        if(productDetails.getProductName().isEmpty())return false;
        if (productDetails.getCategoryName().isEmpty())return false;



        return true;
    }
}
