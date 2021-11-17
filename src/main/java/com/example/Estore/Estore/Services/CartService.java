package com.example.Estore.Estore.Services;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.Cart.CartItemRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
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

    public CartItemEntity addCartItem(String userId, Long productId, Integer quantity) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        ProductEntity productEntity = productRepository.findByProductId(productId);
        CartItemEntity cartItemEntity = new CartItemEntity();
        cartItemEntity.setProductEntity(productEntity);
        cartItemEntity.setCreatedDate(new Date());
        cartItemEntity.setProductName(productEntity.getProductName());
        cartItemEntity.setUserEntity(userEntity);
        cartItemEntity.setQuantity(quantity);
        cartItemEntity.setTotalPrice(productEntity.getPrice() * quantity);
        cartItemRepository.save(cartItemEntity);
        return cartItemEntity;
    }

    public List<CartItemEntity> findByUserId(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        return cartItemRepository.findByCartStatus(userEntity);
    }


    public void removeProductFromCart(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        cartItemRepository.deleteProduct(productId,userEntity);

    }

    public String updateCartItem(UserDto user ,Long productId, Integer quantity,Integer reducequantity) {
        ProductEntity productEntity = productRepository.findByProductId(productId);
        UserEntity userEntity= new UserEntity();
        BeanUtils.copyProperties(user,userEntity);
        CartItemEntity cartItemEntity=cartItemRepository.findByUserEntityANDProductId(userEntity,productId);
        if (cartItemEntity==null){
            return "Cart is not active";
        }

        cartItemEntity.setQuantity(cartItemEntity.getQuantity()+quantity);
        cartItemEntity.setQuantity(cartItemEntity.getQuantity()-reducequantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice()+(productEntity.getPrice()*quantity));
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice()-(productEntity.getPrice()*reducequantity));
        cartItemRepository.save(cartItemEntity);
        return "Cart updated";


}}
