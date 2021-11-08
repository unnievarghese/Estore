package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.UserDetailsRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "home")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "this is get";
    }

    @PostMapping
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue= new ModelMapper().map(createUser,UserRest.class);

        return returnValue;
    }
}
