package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;

import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import com.example.Estore.Estore.io.Repositories.WishList.WishListRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;

    /**
     * Method to add a product to the wishlist.
     * @param userDto
     * @param ProductId
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public WishListEntity addProductToWishList(UserDto userDto, Long ProductId) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        Optional<WishListEntity> wishlistEntity = wishListRepository.findAllByUserEntity(userEntity);
        WishListEntity wishlistEntity1 = new WishListEntity();
        if (wishlistEntity.isEmpty()) {
            wishlistEntity1.setUserEntity(userRepository.findByUserId(userEntity.getUserId()));
            ProductEntity productEntity = productRepository.findById(ProductId).get();
            wishlistEntity1.setUserReferenceId(userEntity.getId());

            wishlistEntity1.setProductEntity(productEntity);
            wishlistEntity1.setProductEntityList(List.of(productEntity));
        } else {
            wishlistEntity1 = wishlistEntity.get();
            ProductEntity productEntity = productRepository.findById(ProductId).get();
            for (int i = 0; i < wishlistEntity1.getProductEntityList().size(); i++) {
                if (productEntity == wishlistEntity1.getProductEntityList().get(i)) {

                    throw new ClientSideException(Messages.PRODUCT_ALREADY_EXISTS.getMessage());
                }

            }
            wishlistEntity1.getProductEntityList().add(productEntity);


        }
        return wishListRepository.save(wishlistEntity1);
    }

    /**
     * Method to view all the wishlist items
     * @param userDto
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public Optional<WishListEntity> getWishListItem(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);
        Optional<WishListEntity> wishListEntityList = wishListRepository.findAllByUserEntity(userEntity);

        if (wishListRepository.findAllByUserEntity(userEntity).get().getProductEntityList().isEmpty())
        {
            throw new ClientSideException(Messages.EMPTY_RECORD.getMessage());
        }
        else

            return wishListEntityList;


    }

    /**
     * Method to remove a product from the wishlist.
     * @param user
     * @param productId
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public String removeProductFromWishlist(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        Optional<WishListEntity> wishListEntity=wishListRepository.findAllByUserEntity(userEntity);
        WishListEntity wishListEntity1=wishListEntity.get();
        Long wishListId=wishListEntity1.getWishListId();
        int i=0;
        while (i<=wishListEntity1.getProductEntityList().size())
        {
            if (wishListEntity1.getProductEntityList().isEmpty())
            {
                throw new ClientSideException(Messages.EMPTY_RECORD.getMessage());
            }
            else if (Objects.equals(productId, wishListEntity1.getProductEntityList().get(i).getProductId()))
            {
                wishListRepository.deleteProduct(wishListId,productId);
                return Messages.DELETE_PRODUCT.getMessage();
            }
            else if (!Objects.equals(productId, wishListEntity1.getProductEntityList().get(i).getProductId()))
                throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());
            i++;
        }
        return null;
    }
}





