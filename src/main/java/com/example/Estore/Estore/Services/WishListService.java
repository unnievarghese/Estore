package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.WishList.WishListDto;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;

public interface WishListService {
    WishListEntity addProductFromWishList(String id, Long productId);


}