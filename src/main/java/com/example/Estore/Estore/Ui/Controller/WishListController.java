package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ServiceImpl.WishListService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.WishListRequest.WishListRest;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;

import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;

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
    @Autowired
    ProductRepository productRepository;


    /**
     * Method is used to add product to wishlist
     * @param productId is of type long,contains unique long id generated for each product.
     * @return WishlistRest
     * @throws ClientSideException throws custom exception
     */
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
            if (productRepository.findById(productId).isEmpty())
            {
                throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());
            }
            WishListEntity wishlistEntity = wishListService.addProductToWishList(user, productId);
            return new ModelMapper().map(wishlistEntity, WishListRest.class);
        }

    }

    /**
     * Method to get the list of product and its details from the wishlist.
     * @return wishlistEntityList
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/get")
    public Optional<WishListEntity> getWishlistItems()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());


        Optional<WishListEntity> wishListEntityList=wishListService.getWishListItem(user);

        return wishListEntityList;

    }

    /**
     * Method to delete a product from the wishlist
     * @param ProductId is of type long,contains unique long id generated for each product.
     * @return String
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path = "/delete/{ProductId}")
    public String deleteProduct(@PathVariable (value="ProductId") Long ProductId) throws Exception {
        if (ProductId == null) {
            throw new ClientSideException (Messages.PRODUCT_ID_NOT_FOUND.getMessage());
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
             wishListService.removeProductFromWishlist(user, ProductId);
            return Messages.DELETE_PRODUCT.getMessage();


        }
    }
}
