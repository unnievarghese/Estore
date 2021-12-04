package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

/**
 * This interface is autowired into controller class and is used to call service methods.
 */
public interface UserService extends UserDetailsService {

    /**
     * Method to create Buyer,gets user details from controller and saves it to database.
     * @param user contains dto of user entered details.
     * @return UserDto
     */
    UserDto createUser(UserDto user);

    /**
     * Method to create Seller,gets user details from controller and saves it to database.
     * @param user contains dto of details entered by admin.
     * @return UserDto
     */
    UserDto createSeller(UserDto user);

    /**
     * Method to get a single user.
     * @param email contains email entered by the user.
     * @return UserDto
     */
    UserDto getUser(String email);

    /**
     * Method to get a single user.
     * @param userId contains the unique string id generated for each user.
     * @return UserDto
     */
    UserDto fetchUser(String userId);

    /**
     * Method to list all users in the database.
     * @param page exact page to be displayed.
     * @param limit number of users per page.
     * @return List<UserDto>
     */
    List<UserDto> getUsers(int page, int limit);

    /**
     * Method to update user details.Gets the user details to be changed and saves it to database.
     * @param userId contains the unique string id generated for each user.
     * @param user contains the details to be updated to the user.
     * @return  UserDto
     */
    UserDto updateUser(String userId,UserDto user);

    /**
     *
     * Method to update user address.Gets the details to be changed and saves it to database.
     * @param addressid contains the addressid to be updates.
     * @param address contains the details to be updated to the address.
     * @return  AddressDto
     */
    AddressDto updateAddress(String addressid, AddressDto address,String userId);

    /**
     * Method to delete the user,gets the user and deletes it from the database.
     * @param userId contains the unique string id generated for each user.
     * @return integer.
     */
    int deleteUser(String userId);

    /**
     * Method to deactivate the user,gets the user and toggles the email verification status to false.
     * @param userId contains the unique string id generated for each user.
     * @return integer.
     */
    int deactivateUser(String userId);

    /**
     * Method to activate the user,gets the user and toggles the email verification status to false.
     * @param userId contains the unique string id generated for each user.
     * @return integer.
     */
    String activateUser(String userId);

    /**
     * Method to verify email token, gets token from controller and verifies it.
     * @param token contains token got from user side.
     * @return  Boolean
     */
    Boolean verifyEmailToken(String token);

    /**
     * Method to send email for password resetting.
     * @param email contains email entered by user.
     * @return Boolean.
     */
    Boolean requestPasswordReset(String email);

    /**
     * Method to resend email for verification of account whose email is not verified or token is expired.
     * @param email contains email given by user.
     * @return Boolean
     */
    Boolean requestEmailResend(String email);

    /**
     * Method to verify PasswordReset Token, gets token from controller and verifies it.
     * @param token contains token from user side.
     * @param password1 contains password entered by user.
     * @param password2 contains password re-entered by user.
     * @return Boolean
     */
    Boolean verifyPasswordResetToken(String token,String password1,String password2);

    /**
     * Method to check if the record exists and to check the authorization.
     * @param userId unique userId generated for the user.
     */
    void hasAccess(String userId);
}
