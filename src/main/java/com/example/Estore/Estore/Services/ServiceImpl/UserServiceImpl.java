package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Security.SecurityConstants;
import com.example.Estore.Estore.Services.EmailService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.*;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.CardDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.io.Entity.User.*;
import com.example.Estore.Estore.io.Repositories.User.AddressRepository;
import com.example.Estore.Estore.io.Repositories.User.PasswordResetTokenRepository;
import com.example.Estore.Estore.io.Repositories.User.RoleRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * This class implements UserService interface and provides some business functionalities.
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Inject UserRepository dependency
     */
    @Autowired
    UserRepository userRepository;
    /**
     * Inject BCryptPasswordEncoder dependency
     */
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * Inject utils dependency
     */
    @Autowired
    Utils utils;
    /**
     * Inject RoleRepository dependency
     */
    @Autowired
    RoleRepository roleRepository;
    /**
     * Inject EmailService dependency
     */
    @Autowired
    EmailService emailService;
    /**
     * Inject PasswordResetTokenRepository dependency
     */
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    /**
     * Inject AddressRepository dependency
     */
    @Autowired
    AddressRepository addressRepository;

    /**
     * Method to create Buyer,gets user details from controller and saves it to database.
     * @param user contains dto of user entered details.
     * @return UserDto
     * @throws ClientSideException Throws customs exceptions.
     */
    @Override
    public UserDto createUser(UserDto user) throws ClientSideException{

        UserEntity alreadyExsistingUser = userRepository.findByEmail(user.getEmail());
        if(alreadyExsistingUser != null && alreadyExsistingUser.getEmailVerificationStatus())
            throw new ClientSideException(Messages.RECORD_ALREADY_EXISTS.getMessage());
        if(alreadyExsistingUser != null && !alreadyExsistingUser.getEmailVerificationStatus())
            throw new ClientSideException(Messages.RESEND_EMAIL.getMessage());

        for (int i = 0; i < user.getAddress().size(); i++) {
            AddressDto addressDto = user.getAddress().get(i);
            addressDto.setUserDetails(user);
            addressDto.setAddressId(utils.generateAddressId(30));
            user.getAddress().set(i, addressDto);
        }
        if (user.getCardDetails() != null) {
            CardDto cardDto = user.getCardDetails();
            cardDto.setUserCardDetails(user);
            cardDto.setCardId(utils.generateCardId(30));
            user.setCardDetails(cardDto);
        }
        UserEntity userEntity = new ModelMapper().map(user, UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));
        RoleEntity roleEntity = roleRepository.findByName("ROLE_BUYER");
        Collection<RoleEntity> arrayList = new ArrayList<RoleEntity>();
        arrayList.add(roleEntity);
        userEntity.setRoles(arrayList);

        String token = utils.generateEmailVerificationToken(userEntity.getUserId());
        userEntity.setEmailVerificationToken(token);
        UserEntity storedUserDetails;
        try {
            storedUserDetails = userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        String link = SecurityConstants.USER_CREATE_EMAIL_LINK + token;
        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(userEntity.getEmail(), emailBuilder.
                buildRegistrationContent(userEntity.getFirstName(), link));



        return new ModelMapper().map(storedUserDetails,UserDto.class);


    }

    /**
     * Method to verify email token, gets token from controller and verifies it.
     * @param token contains token got from user side.
     * @return  Boolean
     */
    @Override
    public Boolean verifyEmailToken(String token) {
        UserEntity userEntity = userRepository.findByEmailVerificationToken(token);
        if (userEntity == null)
            throw new ClientSideException(Messages.TOKEN_NOT_FOUND.getMessage());

        if(Utils.hasTokenExpired(token)) throw new ClientSideException((Messages.EMAIL_TOKEN_EXPIRED.getMessage()));
        userEntity.setEmailVerificationToken(null);
        userEntity.setEmailVerificationStatus(Boolean.TRUE);
        try{
            userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        return true;
    }

    /**
     * Method to resend email for verification of account whose email is not verified or token is expired.
     * @param email contains email given by user.
     * @return Boolean
     * @throws ClientSideException Throws custom exception.
     */
    @Override
    public Boolean requestEmailResend(String email) throws ClientSideException{;
        UserEntity alreadyExsistingUser = userRepository.findByEmail(email);
        if (alreadyExsistingUser == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());
        if(alreadyExsistingUser.getEmailVerificationStatus())
            throw new ClientSideException(Messages.RECORD_ALREADY_EXISTS.getMessage());
        String token = utils.generateEmailVerificationToken(alreadyExsistingUser.getUserId());
        alreadyExsistingUser.setEmailVerificationToken(token);
        try{
            userRepository.save(alreadyExsistingUser);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        String link = SecurityConstants.USER_CREATE_EMAIL_LINK + token;
        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(alreadyExsistingUser.getEmail(), emailBuilder.
                buildRegistrationContent(alreadyExsistingUser.getFirstName(), link));
        return false;
    }

    /**
     * Method to send email for password resetting.
     * @param email contains email entered by user.
     * @return Boolean.
     */
    @Override
    public Boolean requestPasswordReset(String email) {

        UserEntity userEntity = userRepository.findByEmail(email);
        if(userEntity == null)
            throw new ClientSideException(Messages.EMAIL_NOT_FOUND.getMessage());

        String token = new Utils().generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        try{
            passwordResetTokenRepository.save(passwordResetTokenEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        String link = SecurityConstants.PASSWORD_EMAIL_LINK+token;
        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(userEntity.getEmail(),emailBuilder.buildPasswordResetContent(userEntity.getFirstName(),link));
        return true;
    }

    /**
     * Method to verify PasswordReset Token, gets token from controller and verifies it.
     * @param token contains token from user side.
     * @param password1 contains password entered by user.
     * @param password2 contains password re-entered by user.
     * @return Boolean
     */
    @Override
    public Boolean verifyPasswordResetToken(String token,String password1,String password2) {

        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null)
            throw new ClientSideException(Messages.TOKEN_NOT_FOUND.getMessage());

        if (!password1.equals(password2))
            throw new ClientSideException(Messages.PASSWORD_NOT_MATCHING.getMessage());

        if(Utils.hasTokenExpired(token)) throw new ClientSideException((Messages.PASSWORD_TOKEN_EXPIRED.getMessage()));

            UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
            userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(password1));
            try{
                userRepository.save(userEntity);
            }
            catch (Exception e){
                throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
            }
            passwordResetTokenRepository.delete(passwordResetTokenEntity);
        return true;
    }

    /**
     * Method to list all users in the database.
     * @param page exact page to be displayed.
     * @param limit number of users per page.
     * @return List<UserDto>
     */
    @Override
    public List<UserDto> getUsers(int page, int limit) {
        List<UserDto> returnValue = new ArrayList<>();
        Pageable pageableRequest = PageRequest.of(page,limit);
        Page<UserEntity> usersPage = userRepository.findAll(pageableRequest);
        List<UserEntity> users = usersPage.getContent();

        for (UserEntity userEntity : users){
            UserDto userDto = new ModelMapper().map(userEntity,UserDto.class);
            returnValue.add(userDto);
        }
        return returnValue;
    }
    /**
     * Method to create Seller,gets user details from controller and saves it to database.
     * @param user contains dto of details entered by admin.
     * @return UserDto
     * @throws ClientSideException Throws customs exceptions.
     */
    public UserDto createSeller(UserDto user) throws ClientSideException{
        if(userRepository.findByEmail(user.getEmail()) != null)
            throw new ClientSideException(Messages.RECORD_ALREADY_EXISTS.getMessage());
        for (int i=0;i<user.getAddress().size();i++) {
            AddressDto addressDto = user.getAddress().get(i);
            addressDto.setUserDetails(user);
            addressDto.setAddressId(utils.generateAddressId(30));
            user.getAddress().set(i, addressDto);
        }
        if(user.getCardDetails() != null) {
            CardDto cardDto = user.getCardDetails();
            cardDto.setUserCardDetails(user);
            cardDto.setCardId(utils.generateCardId(30));
            user.setCardDetails(cardDto);
        }
        UserEntity userEntity =new ModelMapper().map(user,UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));
        RoleEntity roleEntity = roleRepository.findByName("ROLE_SELLER");
        Collection<RoleEntity> arrayList = new ArrayList<RoleEntity>();
        arrayList.add(roleEntity);
        userEntity.setRoles(arrayList);

        UserEntity storedUserDetails;
        try{
            storedUserDetails = userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }

        return new ModelMapper().map(storedUserDetails,UserDto.class);

    }

    /**
     * Method to update user details.Gets the user details to be changed and saves it to database.
     * @param userId contains the unique string id generated for each user.
     * @param user contains the details to be updated to the user.
     * @return  UserDto
     * @throws ClientSideException Throws custom exceptions.
     */
    @Override
    public UserDto updateUser(String userId, UserDto user) throws ClientSideException{
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (user.getFirstName() != null){
            userEntity.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null){
            userEntity.setLastName(user.getLastName());
        }
        if (user.getPhoneNumber() != null){
            userEntity.setPhoneNumber(user.getPhoneNumber());
        }
        if (user.getEmail() != null){
            userEntity.setEmail(user.getEmail());
        }
        UserEntity updatedUserDetails;
        try{
            updatedUserDetails = userRepository.save(userEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        return new ModelMapper().map(updatedUserDetails,UserDto.class);
    }

    /**
     * Method to update user address.Gets the details to be changed and saves it to database.
     * @param addressid contains the addressid to be updates.
     * @param address contains the details to be updated to the address.
     * @return  AddressDto
     * @throws ClientSideException Throws custom exceptions.
     */
    @Override
    public AddressDto updateAddress(String addressid, AddressDto address,String userId) throws ClientSideException{
        UserEntity userEntity = userRepository.findByUserId(userId);
        AddressEntity addressEntity = addressRepository.findByAddressId(addressid);

        if(userEntity == null || addressEntity == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        UserEntity userDetails = addressRepository.findByAddressId(addressid).getUserDetails();
        if(userDetails == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();
        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new ClientSideException(Messages.NO_ACCESS.getMessage());

        if(userEntity != userDetails && !auth.contains("ROLE_ADMIN")){
            throw new ClientSideException(Messages.NO_ACCESS.getMessage());
        }
        if(address.getCity() != null){
            addressEntity.setCity(address.getCity());
        }
        if(address.getCountry() != null){
            addressEntity.setCountry(address.getCountry());
        }
        if(address.getStreetName() != null){
            addressEntity.setStreetName(address.getStreetName());
        }
        if(address.getPostalCode() != null){
            addressEntity.setPostalCode(address.getPostalCode());
        }
        AddressEntity updatedAddress;
        try{
            updatedAddress = addressRepository.save(addressEntity);
        }
        catch (Exception e){
            throw new ClientSideException(Messages.FAILED_DB_SAVE.getMessage());
        }
        return new ModelMapper().map(updatedAddress,AddressDto.class);
    }

    /**
     * Method to delete the user,gets the user and deletes it from the database.
     * @param userId contains the unique string id generated for each user.
     * @return integer.
     */
    @Override
    public int deleteUser(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Long id = userEntity.getId();
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByUser(id);
        if(passwordResetTokenEntity != null){
            passwordResetTokenRepository.delete(passwordResetTokenEntity);
        }
        userRepository.deleteAddress(userEntity);
        userRepository.deleteCard(userEntity);
        userRepository.deleteRole(userEntity);
        return userRepository.deleteUser(userEntity);
    }

    /**
     * Method to deactivate the user,gets the user and toggles the email verification status to false.
     * @param userId contains the unique string id generated for each user.
     * @return integer.
     */
    @Override
    public int deactivateUser(String userId) {
        return userRepository.deactivateUser(userId);
    }

    /**
     * Method to activate the user,gets the user and toggles the email verification status to true if
     * its admin in case it's the user itself then sends an email verification link to the user
     * @param userId contains the unique string id generated for each user.
     * @return String.
     */
    @Override
    public String activateUser(String userId) {
        String auth = SecurityContextHolder.getContext().getAuthentication().toString();
        if(auth.contains("ROLE_ADMIN")) {
            if(userRepository.activateUser(userId)==1) return "success";
        }
        UserEntity userEntity = userRepository.findByUserId(userId);
        String token = utils.generateEmailVerificationToken(userEntity.getUserId());
        userEntity.setEmailVerificationToken(token);
        userRepository.save(userEntity);
        String link = SecurityConstants.USER_CREATE_EMAIL_LINK + token;
        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(userEntity.getEmail(), emailBuilder.
                buildUserActivateContent(userEntity.getFirstName(), link));
        return "emailsent";
    }

    /**
     * Method to get a single user.
     * @param email contains email entered by the user.
     * @return UserDto
     * @throws ClientSideException Throws custom exception.
     */
    @Override
    public UserDto getUser(String email) throws ClientSideException{
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new ClientSideException(Messages.EMAIL_NOT_FOUND.getMessage());

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }

    /**
     * Method to get a single user.
     * @param userId contains the unique string id generated for each user.
     * @return UserDto
     * @throws ClientSideException Throws custom exception.
     */
    @Override
    public UserDto fetchUser(String userId) throws ClientSideException{
        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());
        return new ModelMapper().map(userEntity,UserDto.class);
    }
    /**
     * Method to load a user by its username(email).
     * @param email email entered by user while doing login.
     * @return UserDetails.
     */
    @Override
    public UserDetails loadUserByUsername(String email){
        UserEntity userEntity = userRepository.findByEmail(email);;
        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<AuthorityEntity> authorityEntity = new HashSet<>();

        Collection<RoleEntity> roles = userEntity.getRoles();

        roles.forEach((role) -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            authorityEntity.addAll(role.getAuthorities());
        });

        authorityEntity.forEach((authorityEntityobj) -> {
            authorities.add(new SimpleGrantedAuthority(authorityEntityobj.getName()));
        });
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),
                userEntity.getEmailVerificationStatus(),
                true,true,true,authorities);
    }

    /**
     * Method to check if the record exists and to check the authorization.
     * @param userId unique userId generated for the user.
     * @throws ClientSideException Throws clientSideException.
     */
    @Override
    public void hasAccess(String userId) throws ClientSideException{
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity == null)
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        String  auth = SecurityContextHolder.getContext().getAuthentication().toString();
        if(!auth.contains(userEntity.getEmail()) && !auth.contains("ROLE_ADMIN"))
            throw new ClientSideException(Messages.NO_ACCESS.getMessage());
    }
}
