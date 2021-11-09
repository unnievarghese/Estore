package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.CategoryDto;
import com.example.Estore.Estore.Shared.dto.ItemDto;
import com.example.Estore.Estore.Shared.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto user);
    UserDto getUser(String email);
    CategoryDto createCategory(CategoryDto category);
    ItemDto createItem(ItemDto item);
}
