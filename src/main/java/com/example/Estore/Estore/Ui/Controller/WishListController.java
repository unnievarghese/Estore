package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Services.WishListService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    UserService userService;
    @Autowired
    WishListService wishListService;


    @PostMapping(path = "/add/{productId}")
    public ResponseEntity<WishListEntity> addToWishList(@PathVariable(value="productId") Long productId)
    {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        UserDto user=userService.getUser(auth.getName());
        WishListEntity wishListEntity=wishListService.addProductToWishList(user.getUserId(),productId);
        return new ResponseEntity<>(wishListEntity, HttpStatus.OK);
    }

}
