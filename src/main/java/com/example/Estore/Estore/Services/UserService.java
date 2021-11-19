package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.User.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    UserDto updateUser(String id,UserDto user);
}
