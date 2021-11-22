package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserDetailsRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.UserRest;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "home")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    public String getProduct()
    {
        return "get product was called";
    }

    @GetMapping
    public String getuser(){
        return "this is get";
    }

    @PostMapping(path="/create-user")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue= new ModelMapper().map(createUser,UserRest.class);

        return returnValue;
    }

    @PutMapping(path="/update-user/{id}")
    public UserRest updateUser(@PathVariable String id, Principal principal, @RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(id);

        if(!userEntity.getEmail().equals(principal.getName())) throw new IllegalAccessException("you have no access to this account");
        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto updatedUser = userService.updateUser(id,userDto);
        UserRest returnValue= new ModelMapper().map(updatedUser,UserRest.class);
        return returnValue;
    }
}
