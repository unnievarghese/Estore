package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.CartService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartCost;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.OperationStatusModel;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationName;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationStatus;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;


    /**
     * Method for adding products and quantity to cart of login user
     * @param productId unique id of product
     * @param quantity  count of products needed to be added to cart
     * @return CartItemRest Added item ,quantity, price,created date
     * @throws ClientSideException Custom Exception
     */

//  http://localhost:8080/Estore/cart/addProduct/{productId}?quantity={?}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })


    @PostMapping(path = "/addProduct/{productId}")

    public CartItemRest addCartItem(@PathVariable(value = "productId") Long productId,
                                    @RequestParam(value = "quantity") Integer quantity) throws ClientSideException {
        if (productId == null || !(quantity > 0)) {
            throw new ClientSideException(Messages.PRODUCT_ID_NOT_FOUND.getMessage());
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartItemEntity cartItemEntity = cartService.addCartItem(user.getUserId(), productId, quantity);
            return new ModelMapper().map(cartItemEntity, CartItemRest.class);
        }
    }


    /**
     * Method for fetching all the items in a cart of Login user
     * @return CartCost All the items in cart along with total cost and discount if any
     */

//  http://localhost:8080/Estore/cart/fetch
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @GetMapping(path = "/fetch")
    public CartCost getCartByUserId() throws ClientSideException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        CartCost cartCost = cartService.findByUserId(user);
        return cartCost;

    }

    /**
     * Method for deleting particular product from Cart of login user
     * @param productId unique id of product
     * @return OperationStatusModel whether the operation is success or not
     * @throws ClientSideException throws custom Exception
     */
//  http://localhost:8080/Estore/cart/deleteProduct/{productId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @DeleteMapping(path = "/deleteProduct/{productId}")
    public OperationStatusModel removeProduct(@PathVariable(value = "productId") Long productId) throws ClientSideException {
        if (productId == null) {
            throw new ClientSideException(Messages.PRODUCT_ID_NOT_FOUND.getMessage());
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

    /**
     * Method for adding quantity of active product inside cart of login user
     * @param productId unique id of product
     * @param quantity  count of product that needs to be increased
     * @return CartItemRest
     * @throws ClientSideException throws custom Exception
     */
//  http://localhost:8080/Estore/cart/product/addQuantity/{productId}?quantity={count}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PutMapping("product/addQuantity/{productId}")
    public CartItemRest addQuantity(@PathVariable(value = "productId") Long productId,
                                    @RequestParam(value = "quantity", defaultValue = "0") Integer quantity) throws ClientSideException {

        if (productId == null) {
            throw new ClientSideException(Messages.PRODUCT_ID_NOT_FOUND.getMessage());
        } else {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartItemRest cartItemRest = cartService.addQuantity(user, productId, quantity);

            return cartItemRest;
        }
    }

    /**
     * Method for reducing quantity of active product inside cart of login user
     * @param productId unique id of product
     * @param quantity  count of products that needs to be reduced
     * @return CartItemRest
     * @throws ClientSideException custom exception
     */
//  http://localhost:8080/Estore/cart/product/reduceQuantity/{productId}?quantity={count}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PutMapping("product/reduceQuantity/{productId}")
    public CartItemRest reduceQuantity(@PathVariable(value = "productId") Long productId,
                                       @RequestParam(value = "quantity", defaultValue = "0") Integer quantity) throws ClientSideException {

        if (productId == null) {
            throw new ClientSideException(Messages.PRODUCT_ID_NOT_FOUND.getMessage());
        } else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            CartItemRest cartItemRest = cartService.reduceQuantity(user, productId, quantity);

            return cartItemRest;
        }
    }

    /**
     * Method for adding wishlist to cart
     * @param wishlistid unique id of wishlist
     * @param quantity   count of products that needs to be added to cart
     * @return String message whether wishlist is added to cart or not
     */
//  http://localhost:8080/Estore/cart/addWishlist/{wishlistid}?quantity={count}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PostMapping(path = "/addWishlist/{wishlistid}")
    public String addWishlistToCart(@PathVariable(value = "wishlistid") long wishlistid,
                                    @RequestParam(value = "quantity", defaultValue = "0") Integer quantity){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        String wishListRest = cartService.addWishlistToCart(user, wishlistid, quantity);
        return wishListRest;

    }

    /**
     * Method for fetching a particular product from cart
     * @param productId unique id of product
     * @return CartItemRest
     */

//  http://localhost:8080/Estore/cart/fetch/{productId}
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @GetMapping(path = "/fetch/{productId}")
    public CartItemRest getCartByProductId(@PathVariable(value = "productId") Long productId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        CartItemRest cartItemRest = cartService.findByProductId(user, productId);
        return cartItemRest;
    }

    /**
     * Method for adding discount for a particular user by the seller in case of any disputes in previous orders
     * @param discount integer value of discount that needs to be applied
     * @return String Discount applied successfully
     */

//  http://localhost:8080/Estore/cart/applyDiscount
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })

    @PostMapping(path = "/applyDiscount")
    public String applyDiscount(@RequestParam(value = "discount", defaultValue = "0") int discount) throws ClientSideException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        return cartService.applyPromoCode(user, discount);
    }

    /**
     * Method for sending a cart remainder by admin for proceeding it to check out when cart is abandoned
     * @return String email sent successfully
     */
//  http://localhost:8080/Estore/cart/checkIdleCart

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_ADMIN")
    @PostMapping(path = "/checkIdleCart")
    public ResponseEntity<String> sendCartRemainder() {
        cartService.abandonedCartMail();
        return new ResponseEntity<String>(Messages.EMAIL_SUCCESS.getMessage(), HttpStatus.OK);

    }

}






