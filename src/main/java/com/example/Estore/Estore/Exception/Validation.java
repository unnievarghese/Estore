package com.example.Estore.Estore.Exception;

import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserDetailsRequestModel;

public class Validation {

    public Boolean checkFields(UserDetailsRequestModel userDetails){

        if(userDetails.getFirstName().isEmpty()) return false;
        if(userDetails.getLastName().isEmpty()) return false;
        if(userDetails.getEmail().isEmpty()) return false;
        if(userDetails.getPassword().isEmpty()) return false;
        if(userDetails.getPhoneNumber().isEmpty()) return false;
        if(userDetails.getAddress().isEmpty()) return false;

        return true;
    }
}
