package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.ServiceImpl.WishListService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.WishListRequest.WishListRest;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/wishlist")
public class WishListController {
    @Autowired
    UserService userService;
    @Autowired
    WishListService wishListService;


    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/add/{productId}")
    public WishListRest addToWishlist(@PathVariable(value = "productId") Long productId) throws Exception {
        if (productId==null)
        {
            throw new Exception("product is null");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            //System.out.println(user.getUserId());
            WishListEntity wishlistEntity = wishListService.addProductToWishList(user, productId);
            return new ModelMapper().map(wishlistEntity, WishListRest.class);
        }

    }



    @GetMapping(path = "/get")
    public Optional<WishListEntity> getWishlistItems()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        Optional<WishListEntity> wishListEntityList=wishListService.getWishListItem(user);
        return wishListEntityList;

    }

//    @DeleteMapping(path = "delete/{wishListId}")
//    public String deleteProductFromWishList(@PathVariable(value="ProductId") Long ProductId) {
//        if (ProductId==null)
//        {
//            return "Product not found";
//        }
//        else {
//            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//            UserDto user = userService.getUser(auth.getName());
//            wishListService.deleteProductFromWishlist(user.getId(),ProductId);
//
//            return "Successfully deleted";
//        }
//    }
    @DeleteMapping(path = "/delete/{ProductId}")
    public String deleteProduct(@PathVariable (value="ProductId") Long ProductId) throws Exception {
        if (ProductId == null) {
            throw new Exception("product id not found");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            WishListEntity wishListEntity = wishListService.removeProductFromWishlist(user, ProductId);
            return "Successfully deleted";


        }
    }
}
