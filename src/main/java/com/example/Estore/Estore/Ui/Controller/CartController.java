package com.example.Estore.Estore.Ui.Controller;
import com.example.Estore.Estore.Services.CartService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.OperationStatusModel;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationName;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationStatus;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/add/{productId}")
    public CartItemRest addCartItem(@PathVariable(value = "productId") Long productId,
                                    @RequestParam(value = "quantity") Integer quantity) throws Exception {
        if (productId == null || !(quantity > 0)) {
            throw new Exception("product is null");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartItemEntity cartItemEntity = cartService.addCartItem(user.getUserId(), productId, quantity);
            return new ModelMapper().map(cartItemEntity, CartItemRest.class);
        }
    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/get")
    public List<CartItemEntity> getCartById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        System.out.println(user.getId());
        List<CartItemEntity> cartItemEntityList = cartService.findByUserId(user);
        return cartItemEntityList;

    }

    @DeleteMapping(path = "/delete/{productId}")
    public OperationStatusModel removeProduct(@PathVariable(value = "productId") Long productId) throws Exception {
        if (productId == null) {
            throw new Exception("Record with given " + productId + " is not found");
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            OperationStatusModel returnValue = new OperationStatusModel();
            cartService.removeProductFromCart(user, productId);
            returnValue.setOperationName(RequestOperationName.DELETE.name());
            returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
            return returnValue;

        }
    }

    @PutMapping("/update/{productId}")
    public String updateCartItem(@PathVariable(value = "productId") Long productId,
                                                         @RequestParam(value = "quantity",defaultValue = "0") Integer quantity,
                                          @RequestParam(value = "reduceQuantity",defaultValue = "0") Integer reducequantity) throws Exception{
        if (productId == null) {
            throw new Exception("Record with given is not found");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        return cartService.updateCartItem(user, productId, quantity,reducequantity);
    }
}






