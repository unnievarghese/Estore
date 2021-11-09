package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.CategoryDto;
import com.example.Estore.Estore.Shared.dto.ItemDto;
import com.example.Estore.Estore.Shared.dto.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.CategoryRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.ItemRequestModel;
import com.example.Estore.Estore.Ui.Model.Request.UserDetailsRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.CategoryRest;
import com.example.Estore.Estore.Ui.Model.Response.ItemRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "home")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public String getUser(){
        return "this is get";
    }

    @PostMapping(path="/create-user")
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws Exception {

        if(userDetails.getFirstName().isEmpty()) throw new NullPointerException("The Object is Null");

        UserDto userDto = new ModelMapper().map(userDetails,UserDto.class);
        UserDto createUser = userService.createUser(userDto);
        UserRest returnValue= new ModelMapper().map(createUser,UserRest.class);

        return returnValue;
    }
    @PostMapping(path="/create-category")
    public CategoryRest createCategory(@RequestBody CategoryRequestModel category) throws Exception{

        if(category.getName().isEmpty()) throw new NullPointerException("The Object is Null");

        CategoryDto categoryDto = new ModelMapper().map(category,CategoryDto.class);
        CategoryDto createdCategory = userService.createCategory(categoryDto);
        return new ModelMapper().map(createdCategory,CategoryRest.class);
    }
    @PostMapping(path="/create-item")
    public ItemRest createCategory(@RequestBody ItemRequestModel item) throws Exception{

        if(item.getName().isEmpty()) throw new NullPointerException("The Object is Null");

        ItemDto itemDto = new ModelMapper().map(item,ItemDto.class);
        ItemDto createdItem = userService.createItem(itemDto);
        return new ModelMapper().map(createdItem,ItemRest.class);
    }
}
