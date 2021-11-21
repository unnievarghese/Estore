package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.EmailService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.*;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.CardDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    Utils utils;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    AddressRepository addressRepository;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record Already Exixts");
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
        RoleEntity roleEntity = roleRepository.findByName("ROLE_BUYER");
        Collection<RoleEntity> arrayList = new ArrayList<RoleEntity>();
        arrayList.add(roleEntity);
        userEntity.setRoles(arrayList);

        userEntity.setEmailVerificationToken(utils.generateEmailVerificationToken(userEntity.getUserId()));

        UserEntity storedUserDetails = userRepository.save(userEntity);

        String link = "http://localhost:8080/Estore/home/email-verification?token="
                +userEntity.getEmailVerificationToken();
        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(userEntity.getEmail(),emailBuilder.
                buildRegistrationContent(userEntity.getFirstName(),link));

        return new ModelMapper().map(storedUserDetails,UserDto.class);
    }

    @Override
    public Boolean verifyEmailToken(String token) {
        boolean returnValue = false;

        UserEntity userEntity = userRepository.findByEmailVerificationToken(token);

        if(userEntity != null){
            boolean hastokenExpired = Utils.hasTokenExpired(token);
            if(!hastokenExpired){
                userEntity.setEmailVerificationToken(null);
                userEntity.setEmailVerificationStatus(Boolean.TRUE);
                userRepository.save(userEntity);
                returnValue = true;
            }
        }
        return returnValue;
    }

    @Override
    public Boolean requestPasswordReset(String email) {
        Boolean returnValue =false;

        UserEntity userEntity = userRepository.findByEmail(email);

        if(userEntity == null){
            return returnValue;
        }
        String token = new Utils().generatePasswordResetToken(userEntity.getUserId());

        PasswordResetTokenEntity passwordResetTokenEntity = new PasswordResetTokenEntity();
        passwordResetTokenEntity.setToken(token);
        passwordResetTokenEntity.setUserDetails(userEntity);
        passwordResetTokenRepository.save(passwordResetTokenEntity);

        String link = "http://localhost:8080/Estore/home/password-reset?token="
                +passwordResetTokenEntity.getToken();

        EmailBuilder emailBuilder = new EmailBuilder();
        emailService.send(userEntity.getEmail(),emailBuilder.buildPasswordResetContent(userEntity.getFirstName(),link));
        returnValue = true;
        return returnValue;
    }

    @Override
    public Boolean verifyPasswordResetToken(String token,String password1,String password2) {
        Boolean returnValue =false;
        PasswordResetTokenEntity passwordResetTokenEntity = passwordResetTokenRepository.findByToken(token);

        if (passwordResetTokenEntity == null || !password1.equals(password2)){
            return returnValue;
        }

        UserEntity userEntity = passwordResetTokenEntity.getUserDetails();
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(password1));
        userRepository.save(userEntity);
        returnValue = true;
        passwordResetTokenRepository.delete(passwordResetTokenEntity);
        return returnValue;
    }

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

    public UserDto createSeller(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record Already Exixts");
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

        UserEntity storedUserDetails = userRepository.save(userEntity);
        return new ModelMapper().map(storedUserDetails,UserDto.class);
    }

    @Override
    public UserDto updateUser(String userId, UserDto user) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) throw new UsernameNotFoundException(userId);

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
        UserEntity updatedUserDetails = userRepository.save(userEntity);
        return new ModelMapper().map(updatedUserDetails,UserDto.class);
    }
    @Override
    public AddressDto updateAddress(String addressid, AddressDto address){
        AddressEntity addressEntity = addressRepository.findByAddressId(addressid);

        if(address.getCity() != null){
            System.out.println(address.getCity());
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
        AddressEntity updatedAddress = addressRepository.save(addressEntity);
        return new ModelMapper().map(updatedAddress,AddressDto.class);
    }
    @Override
    public int deleteUser(String userId){
        UserEntity userEntity = userRepository.findByUserId(userId);
        Long id = userEntity.getId();
        userRepository.deleteAddress(id);
        userRepository.deleteCard(id);
        userRepository.deleteRole(id);
        return userRepository.deleteUser(id);
    }
    @Override
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
//        return new UserPrincipal(userEntity);
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

        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),authorities);
    }
}
