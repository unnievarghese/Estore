package com.example.Estore.Estore.Ui.Controller;
import com.example.Estore.Estore.Services.CartService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.OperationStatusModel;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationName;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationStatus;
import com.example.Estore.Estore.Ui.Model.Response.WishListRequest.WishListRest;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
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

//  http://localhost:8080/Estore/cart/addProduct/{productId}?quantity={?}

    @PostMapping(path = "/addProduct/{productId}")
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
//  http://localhost:8080/Estore/cart/fetch

    @GetMapping(path = "/fetch")
    public List<CartItemEntity> getCartById() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<CartItemEntity> cartItemEntityList = cartService.findByUserId(user);
        return cartItemEntityList;

    }
//  http://localhost:8080/Estore/cart/deleteProduct/{productId}

    @DeleteMapping(path = "/deleteProduct/{productId}")
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
//  http://localhost:8080/Estore/cart/product/addQuantity/{productId}?quantity={count}

    @PutMapping("product/addQuantity/{productId}")
    public String addQuantity(@PathVariable(value = "productId") Long productId,
                                                         @RequestParam(value = "quantity",defaultValue = "0") Integer quantity)throws Exception{


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        return cartService.addQuantity(user, productId, quantity);
    }

//   http://localhost:8080/Estore/cart/product/reduceQuantity/{productId}?quantity={count}

    @PutMapping("product/reduceQuantity/{productId}")
    public String reduceQuantity(@PathVariable(value = "productId") Long productId,
                              @RequestParam(value = "quantity",defaultValue = "0") Integer quantity) throws Exception{


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        return cartService.reduceQuantity(user, productId, quantity);
    }


//    http://localhost:8080/Estore/cart/addWishlist/{wishlistid}?quantity={count}

    @PostMapping(path = "/addWishlist/{wishlistid}")
    public String addWishlistToCart(@PathVariable(value = "wishlistid")long wishlistid,
                                          @RequestParam(value = "quantity",defaultValue = "0") Integer quantity){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        String wishListRest=cartService.addWishlistToCart(user,wishlistid,quantity);
        return wishListRest;

    }

}






