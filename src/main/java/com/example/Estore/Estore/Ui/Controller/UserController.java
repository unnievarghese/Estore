package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Exception.Validation;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.AddressRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.PasswordResetModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.EmailRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserDetailsRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.UserRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Processes incoming REST API requests and returns the view to be rendered as a response.
 */
@RestController
@RequestMapping(path = "home")
public class UserController {

    /**
     * Inject UserService dependency.
     */
    @Autowired
    UserService userService;

    /**
     * Method is used to create a Buyer,gets userDetails,creates a dto and passes it to service layer.
     * @param  userDetails contains user entered information.
     * @return             UserRest
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/user/create
    @PostMapping(path="/user/create")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws ClientSideException {
        Validation validation = new Validation();
        if(!validation.checkFields(userDetails)) throw new ClientSideException
                (Messages.MISSING_REQUIRED_FIELD.getMessage());

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }

    /**
     * Method checks token used to verify the email.
     * @param token contains the token generated for email verification.
     * @return      ResponseEntity
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/email/verify
    @GetMapping(path = "/email/verify")
    public ResponseEntity<String> verifyEmailToken(@RequestParam(value = "token") String token) throws ClientSideException{
        boolean isVerified = userService.verifyEmailToken(token);
        if (!isVerified) throw new
                ClientSideException(Messages.EMAIL_ADDRESS_NOT_VERIFIED.getMessage());
        return new ResponseEntity<String>(Messages.EMAIL_ADDRESS_VERIFIED.getMessage(), HttpStatus.OK);
    }

    /**
     * Method sends a new verification link to the given email,if the email is already registered but not verified.
     * @param emailRequestModel contains the email entered by user.
     * @return                  ResponseEntity
     */
//    http://localhost:8080/estore/home/resend/verification
    @PostMapping(path ="/resend/verification")
    public ResponseEntity<String> requestEmailResend(@RequestBody EmailRequestModel emailRequestModel){

        Boolean OperationResult = userService.requestEmailResend(emailRequestModel.getEmail());

        if(OperationResult)
            return new ResponseEntity<String>(Messages.EMAIL_SENT.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.EMAIL_NOT_SENT.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Method is used to create a seller,gets userDetails,creates a dto and passes it to service layer.
     * @param userDetails contains user entered information.
     * @return            UserRest
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/seller/create
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path="/seller/create")
    public UserRest createSeller(@RequestBody UserDetailsRequestModel userDetails)  throws ClientSideException {
        Validation validation = new Validation();
        if(!validation.checkFields(userDetails)) throw new ClientSideException
                (Messages.MISSING_REQUIRED_FIELD.getMessage());

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createSeller(userDto);
        return new ModelMapper().map(createUser,UserRest.class);
    }

    /**
     * Method is used to fetch a single user.
     * @param userId contains the unique string id generated for each user.
     * @return       UserRest
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/user/{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/user/{userId}")
    public UserRest fetchUser(@PathVariable String userId) throws ClientSideException{
        userService.hasAccess(userId);
        UserDto userDto = userService.fetchUser(userId);
        return new ModelMapper().map(userDto,UserRest.class);
    }

    /**
     * Method is used to fetch all users.
     * @param page exact page to be displayed.
     * @param limit number of users per page.
     * @return      List<UserRest>
//     */
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

    /**
     * Method to update user details.
     * @param userId contains the unique string id generated for each user.
     * @param userDetails contains user entered information.
     * @return            UserRest
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/estore/home/user/update{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path="/user/update{userId}")
    public UserRest updateUser(@PathVariable String userId,@RequestBody UserDetailsRequestModel userDetails) throws ClientSideException {
        userService.hasAccess(userId);
        UserDto updatedUser = userService.updateUser(userId,new ModelMapper().map(userDetails,UserDto.class));
        return new ModelMapper().map(updatedUser,UserRest.class);
    }

    /**
     * Method to update a user's address.
     * @param userId contains the unique string id generated for each user.
     * @param addressid contains the unique string addressid generated for each user.
     * @param addressDetails contains user entered information.
     * @return               AddressesRest
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/address/update/{userId}/{addressid}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path="/address/update/{userId}/{addressid}")
    public AddressesRest updateAddress(@PathVariable String userId, @PathVariable String addressid,
                                       @RequestBody AddressRequestModel addressDetails)
                                        throws ClientSideException{
        AddressDto addressDto = new ModelMapper().map(addressDetails,AddressDto.class);
        AddressDto updatedAddress = userService.updateAddress(addressid,addressDto,userId);
        return new ModelMapper().map(updatedAddress,AddressesRest.class);
    }

    /**
     * Method to request a password change.
     * @param emailRequestModel contains email id.
     * @return ResponseEntity<String>
     */
//    http://localhost:8080/estore/home/password/reset
    @PostMapping(path="/password/reset")
    public ResponseEntity<String> requestReset(@RequestBody EmailRequestModel emailRequestModel){

        Boolean OperationResult = userService.requestPasswordReset(emailRequestModel.getEmail());

        if(OperationResult)
            return new ResponseEntity<String>(Messages.EMAIL_SENT.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.EMAIL_NOT_SENT.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Method to set new password.
     * @param passwordResetModel contains user entered new password.
     * @param token password reset token sent to user's email.
     * @return ResponseEntity<String>
     */
//    http://localhost:8080/Estore/home/password/create?token={token}
    @PostMapping(path="/password/create")
    public ResponseEntity<String> setNewPassword(@RequestBody PasswordResetModel passwordResetModel,
                                               @RequestParam(value = "token") String token){
        Boolean isVerified = userService.verifyPasswordResetToken(token,passwordResetModel.getNewPassword(),
                passwordResetModel.getRepeatPassword());
        if(isVerified) return new ResponseEntity<String>(Messages.PASSWORD_CHANGED.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.PASSWORD_NOT_CHANGED.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Method to delete a user permanently.
     * @param userId contains the unique string id generated for each user.
     * @return ResponseEntity<String>
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/user/delete/{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path = "/user/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable String userId) throws ClientSideException{
        userService.hasAccess(userId);
        if (userService.deleteUser(userId) == 1)
            return new ResponseEntity<String>(Messages.DELETE_SUCCESS.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.COULD_NOT_DELETE_RECORD.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Method to deactivate a user.
     * @param userId contains the unique string id generated for each user.
     * @return ResponseEntity<String>
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/Estore/home/user/deactivate/{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/user/deactivate/{userId}")
    public ResponseEntity<String> deactivateUser(@PathVariable String userId) throws ClientSideException{
        userService.hasAccess(userId);
        if (userService.deactivateUser(userId) == 1)
            return new ResponseEntity<String>(Messages.DEACTIVATE_SUCCESS.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.COULD_NOT_DEACTIVATE_RECORD.getMessage(),HttpStatus.EXPECTATION_FAILED);
    }

    /**
     * Method to Activate a user by admin.
     * @param userId contains the unique string id generated for each user.
     * @return ResponseEntity<String>
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/estore/home/user/activate/{userId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/user/activate/{userId}")
    public ResponseEntity<String> activateUserByadmin(@PathVariable String userId) throws ClientSideException{
        int operationResult = userService.activateUserByAdmin(userId);
        if(operationResult == 1)
            return new ResponseEntity<String>(Messages.ACTIVATE_SUCCESS.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.EMAIL_SENT.getMessage(),HttpStatus.OK);
    }

    /**
     * Method to Activate the user by the user.
     * @param emailRequestModel contains the email entered by user.
     * @return                  ResponseEntity.
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/estore/home/user/activate
    @PostMapping(path = "/user/activate")
    public ResponseEntity<String> activateUserByUser(@RequestBody EmailRequestModel emailRequestModel) throws ClientSideException{
        boolean operationResult = userService.activateUserByUser(emailRequestModel.getEmail());
        if(operationResult)
            return new ResponseEntity<String>(Messages.EMAIL_SENT.getMessage(),HttpStatus.OK);
        return new ResponseEntity<String>(Messages.EMAIL_NOT_SENT.getMessage(),HttpStatus.OK);
    }
}