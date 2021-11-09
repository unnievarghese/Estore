package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.*;
import com.example.Estore.Estore.io.Entity.AddressEntity;
import com.example.Estore.Estore.io.Entity.CategoryEntity;
import com.example.Estore.Estore.io.Entity.ItemEntity;
import com.example.Estore.Estore.io.Entity.UserEntity;
import com.example.Estore.Estore.io.Repositories.AddressRepository;
import com.example.Estore.Estore.io.Repositories.CategoryRepository;
import com.example.Estore.Estore.io.Repositories.ItemRepository;
import com.example.Estore.Estore.io.Repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ItemRepository itemRepository;
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
    public UserDto getUser(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);

        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity,returnValue);

        return returnValue;
    }

    @Override
    public CategoryDto createCategory(CategoryDto category) {

        if(categoryRepository.findByName(category.getName()) != null) throw new RuntimeException("Record Already Exixts");

        CategoryEntity categoryEntity = new ModelMapper().map(category,CategoryEntity.class);
        categoryEntity.setCategoryId(utils.generateCategoryId(30));

        CategoryEntity storedCategoryName = categoryRepository.save(categoryEntity);

        return new ModelMapper().map(storedCategoryName,CategoryDto.class);
    }

    @Override
    public ItemDto createItem(ItemDto item) {

        if(itemRepository.findByName(item.getName()) != null) throw new RuntimeException("Record Already Exixts");

        CategoryEntity categoryEntity = categoryRepository.findByName(item.getCategory());

        ItemEntity itemEntity = new ModelMapper().map(item,ItemEntity.class);
        itemEntity.setItemId(utils.generateItemId(30));
        itemEntity.setCategoryDetails(categoryEntity);

        ItemEntity storedItemName = itemRepository.save(itemEntity);

        return new ModelMapper().map(storedItemName,ItemDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) throw new UsernameNotFoundException(email);
        return new User(userEntity.getEmail(),userEntity.getEncryptedPassword(),new ArrayList<>());
    }
}
