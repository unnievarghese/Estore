package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.*;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.CardDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    Utils utils;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record Already Exixts");

        AddressDto addressDto = user.getAddress().get(0);
        addressDto.setUserDetails(user);
        addressDto.setAddressId(utils.generateAddressId(30));
        user.getAddress().set(0,addressDto);

        CardDto cardDto = user.getCardDetails();
        cardDto.setUserCardDetails(user);
        cardDto.setCardId(utils.generateCardId(30));
        user.setCardDetails(cardDto);

        UserEntity userEntity =new ModelMapper().map(user,UserEntity.class);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userEntity.setUserId(utils.generateUserId(30));

        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue =new ModelMapper().map(storedUserDetails,UserDto.class);



        return returnValue;
    }
    @Override
    public UserDto updateUser(String userId, UserDto user) {

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null) throw new UsernameNotFoundException(userId);
        Method[] dtoMethods = user.getClass().getMethods();
        Method[] entityMethods = userEntity.getClass().getMethods();
        for (int i=0;i<dtoMethods.length;i++){
            if(dtoMethods[i].getName().contains("get")){
                try {
                    if(dtoMethods[i].invoke(user) instanceof String){
                        String valueToChange =(String) dtoMethods[i].invoke(user);
                        String methodName = dtoMethods[i].getName();
                        methodName = methodName.replace("get","set");
                        for (int j=0; j<entityMethods.length;j++){
                            if(entityMethods[j].getName().contains(methodName)){
                               entityMethods[j].invoke(userEntity,valueToChange);
                            }
                        }
                    }
                }
                catch (IllegalAccessException | InvocationTargetException e){
                    e.printStackTrace();
                }
            };
        }
        UserEntity updatedUserDetails = userRepository.save(userEntity);
        return new ModelMapper().map(updatedUserDetails,UserDto.class);
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
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
