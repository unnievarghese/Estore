package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Services.WishListService;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import com.example.Estore.Estore.io.Repositories.WishList.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class WishListServiceImpl implements WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public WishListEntity addProductFromWishList(String userId, Long productId) {
        Optional<WishListEntity> wishListEntity= wishListRepository.findByUserId(userId);
        WishListEntity wishListEntity1=new WishListEntity();
        if (wishListEntity.isEmpty())
        {
            wishListEntity1.setUserEntity(userRepository.findByUserId(userId));
            ProductEnity productEnity=productRepository.findById(productId).get();
            wishListEntity1.setProductEntityList(List.of(productEnity));
        }
    }
}
