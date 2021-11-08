package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.AddressDto;
import com.example.Estore.Estore.Shared.dto.CardDto;
import com.example.Estore.Estore.Shared.dto.UserDto;
import com.example.Estore.Estore.io.Entity.AddressEntity;
import com.example.Estore.Estore.io.Entity.UserEntity;
import com.example.Estore.Estore.io.Repositories.AddressRepository;
import com.example.Estore.Estore.io.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public UserDto createUser(UserDto user) {
        if(userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record Already Exixts");

        AddressDto addressDto = user.getAddress().get(0);
        addressDto.setUserDetails(user);
        addressDto.setAddressId("addressid");
        user.getAddress().set(0,addressDto);

        CardDto cardDto = user.getCardDetails();
        cardDto.setUserCardDetails(user);
        cardDto.setCardId("cardid");
        user.setCardDetails(cardDto);

        UserEntity userEntity =new ModelMapper().map(user,UserEntity.class);
        userEntity.setEncryptedPassword("test");
        userEntity.setUserId("testuserid");

        UserEntity storedUserDetails = userRepository.save(userEntity);
        UserDto returnValue =new ModelMapper().map(storedUserDetails,UserDto.class);
        return returnValue;
    }
}
