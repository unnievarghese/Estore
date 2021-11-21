package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto createSeller(UserDto user);
    UserDto getUser(String email);
    List<UserDto> getUsers(int page, int limit);
    UserDto updateUser(String id,UserDto user);
    AddressDto updateAddress(String addressid, AddressDto address);
    int deleteUser(String id);
    Boolean verifyEmailToken(String token);
    Boolean requestPasswordReset(String email);
    Boolean verifyPasswordResetToken(String token,String password1,String password2);
}
