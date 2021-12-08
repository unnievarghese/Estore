package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ServiceImpl.EmailBuilder;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartCost;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import com.example.Estore.Estore.io.Repositories.Cart.CartItemRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import com.example.Estore.Estore.io.Repositories.WishList.WishListRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CartService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    EmailService emailService;


//  Logic for adding products to Cart

    public CartItemEntity addCartItem(String userId, Long productId, Integer quantity) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        ProductEntity productEntity = productRepository.findByProductId(productId);


        if (productRepository.findByProductId(productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        if (productEntity.getQuantity() < quantity)
            throw new ClientSideException(Messages.OUT_OF_STOCK.getMessage());

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);
        if (cartItemEntity != null) {
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
            int discount = cartItemEntity.getDiscount();
            if (discount != 0) {
                cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() +
                        ((productEntity.getPrice() * (discount * 0.01)) * quantity));
                cartItemRepository.save(cartItemEntity);
                return cartItemEntity;
            }
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() + (productEntity.getPrice() * quantity));
            cartItemRepository.save(cartItemEntity);
            return cartItemEntity;
        }

        CartItemEntity newcartItemEntity = new CartItemEntity();
        newcartItemEntity.setProductEntity(productEntity);
        newcartItemEntity.setCreatedDate(new Date());

        newcartItemEntity.setProductName(productEntity.getProductName());
        newcartItemEntity.setUserEntity(userEntity);
        newcartItemEntity.setQuantity(quantity);
        newcartItemEntity.setTotalPrice(productEntity.getPrice() * quantity);
        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        cartItemRepository.save(newcartItemEntity);
        return newcartItemEntity;
    }

//  Logic for fetching all cartItems

    public CartCost findByUserId(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        if (cartItemRepository.findByUserEntity(userEntity).isEmpty())
            throw new ClientSideException(Messages.CART_IS_EMPTY.getMessage());

        List<CartItemRest> cartItemRestList = new ArrayList<>();
        double totalCost = 0;
        for (CartItemEntity cartItem : cartItemRepository.findByCartStatus(userEntity)) {
            cartItemRestList.add(new ModelMapper().map(cartItem, CartItemRest.class));
            totalCost += cartItem.getTotalPrice();
        }
        CartCost cartCost = new CartCost();
        cartCost.setCartItem(cartItemRestList);
        cartCost.setTotalCost(totalCost);
        int discount = cartItemRepository.findByCartStatus(userEntity).get(0).getDiscount();
        if (discount != 0) {
            cartCost.setDiscount(Integer.toString(discount) + "% off ");
        }
        if (discount == 0) {
            cartCost.setDiscount("no discounts applicable");
        }

        return cartCost;

    }

//  Logic for removing a product from cart

    public void removeProductFromCart(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        CartItemEntity cartItemEntity = new CartItemEntity();
        ProductEntity productEntity = productRepository.findByProductId(productId);

        if (cartItemRepository.findByUserEntityANDProductId(userEntity, productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        cartItemRepository.deleteProduct(productId, userEntity);


    }

//  Logic for adding quantity of product

    public CartItemRest addQuantity(UserDto user, Long productId, Integer quantity) {
        ProductEntity productEntity = productRepository.findByProductId(productId);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);
        if (cartItemEntity == null)
            throw new ClientSideException(Messages.CART_NOT_ACTIVE.getMessage());
        int discount = cartItemEntity.getDiscount();
        if (discount != 0) {
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() +
                    ((productEntity.getPrice() * (discount * 0.01)) * quantity));
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
            cartItemRepository.save(cartItemEntity);
            CartItemRest cartItemRest = new ModelMapper().map(cartItemEntity, CartItemRest.class);

            return cartItemRest;
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() + (productEntity.getPrice() * quantity));
        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        cartItemRepository.save(cartItemEntity);
        CartItemRest cartItemRest = new ModelMapper().map(cartItemEntity, CartItemRest.class);

        return cartItemRest;

    }
//  Logic for reducing quantity of product

    public CartItemRest reduceQuantity(UserDto user, Long productId, Integer quantity) {
        ProductEntity productEntity = productRepository.findByProductId(productId);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);

        if (cartItemEntity == null)
            throw new ClientSideException(Messages.CART_NOT_ACTIVE.getMessage());
        int discount = cartItemEntity.getDiscount();
        if (discount != 0) {
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() -
                    ((productEntity.getPrice() * (discount * 0.01)) * quantity));
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() - quantity);
            cartItemRepository.save(cartItemEntity);
            CartItemRest cartItemRest = new ModelMapper().map(cartItemEntity, CartItemRest.class);

            return cartItemRest;
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() - quantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() - (productEntity.getPrice() * quantity));
        productEntity.setQuantity(productEntity.getQuantity() + quantity);
        productRepository.save(productEntity);
        cartItemRepository.save(cartItemEntity);
        CartItemRest cartItemRest = new ModelMapper().map(cartItemEntity, CartItemRest.class);

        return cartItemRest;

    }

//  Logic for adding wishlist to cart

    public String addWishlistToCart(UserDto user, Long wishListId, Integer quantity) {
        WishListEntity wishListEntity = wishListRepository.findAllByWishListId(wishListId);

        if (wishListRepository.findById(wishListId).isEmpty())
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());

        String UserId = user.getUserId();
        UserEntity userEntity = userRepository.findByUserId(UserId);
        List<ProductEntity> productEntityList = wishListEntity.getProductEntityList();

        for (ProductEntity productEntity : productEntityList) {

            CartItemEntity cartItemEntity = new CartItemEntity();
            cartItemEntity.setProductEntity(productEntity);
            cartItemEntity.setCreatedDate(new Date());
            cartItemEntity.setProductName(productEntity.getProductName());
            cartItemEntity.setUserEntity(userEntity);
            cartItemEntity.setQuantity(quantity);
            cartItemEntity.setTotalPrice(productEntity.getPrice() * quantity);
            productEntity.setQuantity(productEntity.getQuantity() - quantity);
            cartItemRepository.save(cartItemEntity);
            wishListRepository.delete(wishListEntity);
        }
        return "WishList added successfully";
    }

//  Logic for fetching particular product from cart

    public CartCost findByProductId(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);

        if (cartItemRepository.findByUserEntityANDProductId(userEntity, productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        CartCost cartCost = new ModelMapper().map(cartItemEntity, CartCost.class);
        return cartCost;

    }
//  Logic for adding discount to cartItems in case of any disputes in previous orders

    public String applyPromoCode(UserDto user, int discount) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        List<CartItemEntity> cartItemEntity = cartItemRepository.findByUserEntity(userEntity);

        for (CartItemEntity cartItem : cartItemEntity) {

            cartItem.setDiscount(discount);
            if (discount != 0) {

                cartItem.setTotalPrice(cartItem.getTotalPrice() * (discount * 0.01));
            }
            cartItemRepository.save(cartItem);
        }

        return "Discount applied successfully";

    }

//    public void abandonedCartMail(Long id) {
//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(id, userEntity);
//        cartItemRepository.findByUserEntity(userEntity);
//        EmailBuilder emailBuilder = new EmailBuilder();
//        emailService.send(userEntity.getEmail(),emailBuilder.);
//
//    }
}
