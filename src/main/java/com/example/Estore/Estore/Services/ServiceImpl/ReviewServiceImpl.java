package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.ReviewRequest.ReviewRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ReviewRequest.ReviewRest;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.Review.ReviewEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;

import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.Review.ReviewRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ReviewRepository reviewRepository;


    public ReviewRest createReviewByUserId(ReviewRequestModel reviewRequestModel, UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        ReviewEntity reviewEntity = new  ReviewEntity();
        BeanUtils.copyProperties(reviewRequestModel, reviewEntity);
        Optional<ReviewEntity>optionalReviewEntity=reviewRepository.findByProductIdAndUserId(reviewRequestModel.getProductId(),user.getId());
        Optional<ProductEntity> optionalProductEntity=productRepository.findById(reviewRequestModel.getProductId());

        ProductEntity productEntity = productRepository.findById(reviewRequestModel.getProductId()).get();
        if (optionalProductEntity.isEmpty() )
        {
            throw new Exception("Cannot make a review");
        }
        else if (optionalReviewEntity.isEmpty()){

            reviewEntity.setProductEntity(reviewEntity.getProductEntity());
            reviewEntity.setUserEntity(reviewEntity.getUserEntity());
            reviewEntity.setRating(reviewEntity.getRating());
            reviewEntity.setReview(reviewEntity.getReview());
            reviewEntity.setProductName(productEntity.getProductName());
            reviewEntity.setProductEntity(productEntity);
            reviewEntity.setUserEntity(userEntity);
            reviewRepository.save(reviewEntity);
            ReviewRest reviewRest = new ReviewRest();
            reviewRest.setUserName(userEntity.getFirstName());
            BeanUtils.copyProperties(reviewEntity, reviewRest);


            return reviewRest;
        }
        else {
            throw new Exception("review already exists");
        }
    }


//    public Optional<ReviewEntity> findReviewsByUser(UserDto user) {
//        UserEntity userEntity = new UserEntity();
//        BeanUtils.copyProperties(user, userEntity);
//        Optional<ReviewEntity> reviewEntity= reviewRepository.findAllById(userEntity.getId());
//        return reviewEntity;
//    }



    public ReviewRest updateReview(ReviewRequestModel reviewRequestModel, UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        Optional<ReviewEntity> reviewEntity=reviewRepository.findByProductIdAndUserId(reviewRequestModel.getProductId(), userEntity.getId());

        reviewEntity.get().setRating(reviewRequestModel.getRating());
        reviewEntity.get().setReview(reviewRequestModel.getReview());
        ReviewEntity updateReview=reviewRepository.save(reviewEntity.get());
        ReviewRest reviewRest=new ReviewRest();
        reviewRest.setUserName(reviewEntity.get().getUserEntity().getFirstName());
        BeanUtils.copyProperties(updateReview,reviewRest);

        return reviewRest;

    }


    public ReviewEntity deleteReviewByUser(UserDto user, Long productId) {
        Optional<ReviewEntity> reviewEntity=reviewRepository.findAllByUserIdAndProductId(user.getId(),productId);
        ReviewEntity reviewEntity1=reviewEntity.get();
        Long reviewId=reviewEntity1.getReviewId();
        reviewRepository.deleteReview(reviewId,productId);

        return null;
    }

    public List<ReviewRest> findReviewByProductId(Long productId,UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
     List<ReviewEntity> reviewEntityList=reviewRepository.findByProductId(productId);
     if (reviewEntityList.isEmpty())
     {
         throw new Exception("There is no review");
     }
     else{
         List<ReviewRest> reviewRests=new ArrayList<>();
         for (ReviewEntity reviewEntity:reviewEntityList)
         {
             ReviewRest reviewRest=new ReviewRest();
             reviewRest.setUserName(reviewEntity.getUserEntity().getFirstName());
             BeanUtils.copyProperties(reviewEntity,reviewRest);
             reviewRests.add(reviewRest);
         }
         return reviewRests;
     }
    }

    public List<ReviewRest> findReviewByUserId(UserDto user, Long id) throws Exception {
        List<ReviewEntity>reviewEntityList=reviewRepository.findReviewByUser(user.getId());
        if ((reviewEntityList.isEmpty()))
        {
            throw new Exception("no reviews by the user");
        }
        else {
            List<ReviewRest>reviewRests=new ArrayList<>();
            for (ReviewEntity reviewEntity:reviewEntityList)
            {
                ReviewRest reviewRest=new ReviewRest();
                reviewRest.setUserName(reviewEntity.getUserEntity().getFirstName());
                BeanUtils.copyProperties(reviewEntity,reviewRest);
                reviewRests.add(reviewRest);
            }
            return  reviewRests;
        }
    }
}



