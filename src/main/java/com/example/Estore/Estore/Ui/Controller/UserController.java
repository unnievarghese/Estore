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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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


//    http://localhost:8080/Estore/home/user/create
    @PostMapping(path="/user/create")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }

//    http://localhost:8080/Estore/home/email/verify
    @GetMapping(path = "/email/verify")
    public String verifyEmailToken(@RequestParam(value = "token") String token){
        boolean isVerified = userService.verifyEmailToken(token);
        if (isVerified) return "Authentication Successfull";
        return "Authentication Failed";
    }

//    http://localhost:8080/Estore/home/seller/create
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path="/seller/create")
    public UserRest createSeller(@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createSeller(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }
//    http://localhost:8080/Estore/home/user/{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/user/{userId}")
    public UserRest getUser(@PathVariable String userId) throws Exception {

        UserEntity userEntity = userRepository.findByUserId(userId);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        return new ModelMapper().map(userEntity,UserRest.class);
    }
//    http://localhost:8080/Estore/users/fetch
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @GetMapping(path="/users/fetch")
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
//    http://localhost:8080/Estore/home/user/update{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path="/user/update{userId}")
    public UserRest updateUser(@PathVariable String userId,@RequestBody UserDetailsRequestModel userDetails) throws Exception {
        UserEntity userEntity = userRepository.findByUserId(userId);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto updatedUser = userService.updateUser(userId,userDto);
        return new ModelMapper().map(updatedUser,UserRest.class);
    }

//    http://localhost:8080/Estore/home/address/update/{userId}/{addressid}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path="/address/update/{userId}/{addressid}")
    public AddressesRest updateAddress(@PathVariable String userId, @PathVariable String addressid,
                                       @RequestBody AddressRequestModel addressDetails)
                                        throws Exception{
        UserEntity userEntity = userRepository.findByUserId(userId);

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
//    http://localhost:8080/Estore/home/password/reset
    @PostMapping(path="/password/reset")
    public String  requestReset(@RequestBody PasswordResetRequestModel passwordResetRequestModel){

        Boolean OperationResult = userService.requestPasswordReset(passwordResetRequestModel.getEmail());

        if(OperationResult){
            return "Email Sent Successfully.Please use the link in email to change password.";
        }
        return "Email not sent.";
    }
//    http://localhost:8080/Estore/home/password/create?token={token}
    @PostMapping(path="/password/create")
    public String  setNewPassword(@RequestBody PasswordResetModel passwordResetModel,
                                               @RequestParam(value = "token") String token){
        System.out.println("inside cntrl");
        Boolean isVerified = userService.verifyPasswordResetToken(token,passwordResetModel.getNewPassword(),
                passwordResetModel.getRepeatPassword());
        System.out.println(isVerified);
        if(isVerified) return "PASSWORD CHANGED SUCCESSFULLY";

        return "PASSWORD NOT CHANGED";
    }
//    http://localhost:8080/Estore/home/user/delete/{userId}

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path = "/user/delete/{userId}")
    public String deleteUser(@PathVariable String userId) throws Exception{
        UserEntity userEntity = userRepository.findByUserId(userId);

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();

        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new IllegalAccessException("you have no access to this account");
        if (userService.deleteUser(userId) == 1) return "DELETE SUCCESSFULL";
        return "DELETE UNSUCCESSFULL";
    }
}
