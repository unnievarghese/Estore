package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.AddressRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.PasswordResetModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.PasswordResetRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserDetailsRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.UserRest;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.AddressRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "home")
public class UserController {

    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;


//    http://localhost:8080/Estore/home/create-user
    @PostMapping(path="/create-user")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }
//    http://localhost:8080/Estore/home/email-verification
    @GetMapping(path = "/email-verification")
    public String verifyEmailToken(@RequestParam(value = "token") String token){
        boolean isVerified = userService.verifyEmailToken(token);
        if (isVerified) return "Authentication Successfull";
        return "Authentication Failed";
    }
//    http://localhost:8080/Estore/home/create-seller
    @Secured("ROLE_ADMIN")
    @PostMapping(path="/create-seller")
    public UserRest createSeller(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createSeller(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }
//    http://localhost:8080/Estore/home/{id}
    @GetMapping(path = "/{id}")
    public UserRest getUser(@PathVariable String id) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(id);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        return new ModelMapper().map(userEntity,UserRest.class);
    }
//    http://localhost:8080/Estore/home/list-all-users
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/list-all-users")
    public List<UserRest> getUsers(@RequestParam(value = "page",defaultValue = "0") int page,
                                   @RequestParam(value = "limit",defaultValue = "2") int limit){
        List<UserRest> returnValue = new ArrayList<>();
        List<UserDto> users = userService.getUsers(page,limit);

        for (UserDto userDto : users){
            UserRest userRest = new ModelMapper().map(userDto,UserRest.class);
            returnValue.add(userRest);
        }
        return returnValue;
    }
//    http://localhost:8080/Estore/home/update-user/{id}
    @PutMapping(path="/update-user/{id}")
    public UserRest updateUser(@PathVariable String id,@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(id);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto updatedUser = userService.updateUser(id,userDto);
        return new ModelMapper().map(updatedUser,UserRest.class);
    }

//    http://localhost:8080/Estore/home/update-address/{id}/{addressid}
    @PutMapping(path="/update-address/{id}/{addressid}")
    public AddressesRest updateAddress(@PathVariable String id, @PathVariable String addressid,
                                       @RequestBody AddressRequestModel addressDetails)
                                        throws Exception{
        UserEntity userEntity = userRepository.findByUserId(id);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        UserEntity userDetails = addressRepository.findByAddressId(addressid).getUserDetails();

        if(userEntity != userDetails && !auth.contains("ROLE_ADMIN")){
            throw new IllegalAccessException("you have no authorization to change this address");
        }

        AddressDto addressDto = new ModelMapper().map(addressDetails,AddressDto.class);
        AddressDto updatedAddress = userService.updateAddress(addressid,addressDto);
        return new ModelMapper().map(updatedAddress,AddressesRest.class);
    }
//    http://localhost:8080/Estore/home/password-reset-request
    @PostMapping(path="/password-reset-request")
    public String  requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel){

        Boolean OperationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

        if(OperationResult){
            return "Email Sent Successfully.Please use the link in email to change password.";
        }
        return "Email not sent.";
    }
//    http://localhost:8080/Estore/home/password-reset?token={token}
    @PostMapping(path="/password-reset")
    public String  setNewPassword(@RequestBody PasswordResetModel passwordResetModel,
                                               @RequestParam(value = "token") String token){

        Boolean isVerified = userService.verifyPasswordResetToken(token,passwordResetModel.getNewPassword(),
                passwordResetModel.getRepeatPassword());

        if(isVerified) return "PASSWORD CHANGED SUCCESSFULLY";

        return "PASSWORD NOT CHANGED";
    }
//    http://localhost:8080/Estore/home/delete-user/{id}
    @DeleteMapping(path = "/delete-user/{id}")
    public String deleteUser(@PathVariable String id) throws Exception{
        UserEntity userEntity = userRepository.findByUserId(id);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        if (userService.deleteUser(id) == 1) return "DELETE SUCCESSFULL";
        return "DELETE UNSUCCESSFULL";
    }
}
