package com.example.Estore.Estore.Services.ServiceImpl;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.ReviewRequest.ReviewRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ReviewRequest.ReviewRest;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.Review.ReviewEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.Order.OrderRepository;
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
    @Autowired
    OrderRepository orderRepository;

    /**
     * Create review on any product which is there in the database
     * @param reviewRequestModel
     * @param user
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public ReviewRest createReviewByUserId(ReviewRequestModel reviewRequestModel, UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        ReviewEntity reviewEntity = new  ReviewEntity();
        BeanUtils.copyProperties(reviewRequestModel, reviewEntity);
        Optional<ReviewEntity>optionalReviewEntity=reviewRepository.findByProductIdAndUserId(reviewRequestModel.getProductId(),user.getId());
        ProductEntity productEntity = productRepository.findById(reviewRequestModel.getProductId()).get();
        if (reviewRequestModel.getRating()>5)
        {
            throw new ClientSideException(Messages.INVALID_INPUT.getMessage());
        }
        else if (optionalReviewEntity.isEmpty() ){
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
            throw new ClientSideException(Messages.REVIEW_ALREADY_EXISTS.getMessage());
        }
    }

    /**
     * Method to update a review
     * @param reviewRequestModel
     * @param user
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public ReviewRest updateReview(ReviewRequestModel reviewRequestModel, UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);


        Optional<ReviewEntity> reviewEntity=reviewRepository.findByProductIdAndUserId(reviewRequestModel.getProductId(), userEntity.getId());
        if (reviewRequestModel.getRating()>5)
        {
            throw new ClientSideException(Messages.INVALID_INPUT.getMessage());
        }
        else {
            reviewEntity.get().setRating(reviewRequestModel.getRating());
            reviewEntity.get().setReview(reviewRequestModel.getReview());
            ReviewEntity updateReview = reviewRepository.save(reviewEntity.get());
            ReviewRest reviewRest = new ReviewRest();
            reviewRest.setUserName(reviewEntity.get().getUserEntity().getFirstName());
            BeanUtils.copyProperties(updateReview, reviewRest);
            return reviewRest;
        }

    }

    /**
     * Method to delete a review using userId and productId
     * @param user
     * @param productId
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public ReviewEntity deleteReviewByUser(UserDto user, Long productId) {
        Optional<ReviewEntity> reviewEntity=reviewRepository.findAllByUserIdAndProductId(user.getId(),productId);
        if (reviewEntity.isEmpty())
        {
            throw new ClientSideException(Messages.REVIEW_NOT_FOUND.getMessage());
        }
        ReviewEntity reviewEntity1=reviewEntity.get();

        Long reviewId=reviewEntity1.getReviewId();
        reviewRepository.deleteReview(reviewId,productId);

        return null;
    }

    /**
     * Method to get all reviews based on productId
     * @param productId
     * @param user
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public List<ReviewRest> findReviewByProductId(Long productId,UserDto user) throws Exception {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
     List<ReviewEntity> reviewEntityList=reviewRepository.findByProductId(productId);
     if (reviewEntityList.isEmpty())
     {
         throw new ClientSideException(Messages.REVIEW_NOT_FOUND.getMessage());
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

    /**
     * Method to get all reviews done by the user.
     * @param user
     * @return
     * @throws ClientSideException Throws custom exceptions.
     */
    public List<ReviewRest> findReviewByUserId(UserDto user) throws Exception {
        List<ReviewEntity>reviewEntityList=reviewRepository.findReviewByUser(user.getId());
        if ((reviewEntityList.isEmpty()))
        {
            throw new ClientSideException(Messages.EMPTY_RECORD.getMessage());
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



