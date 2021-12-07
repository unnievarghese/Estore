package com.example.Estore.Estore.Ui.Controller;


import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ServiceImpl.ReviewServiceImpl;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.ReviewRequest.ReviewRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.ReviewRequest.ReviewRest;

import com.example.Estore.Estore.io.Entity.Review.ReviewEntity;

import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.Review.ReviewRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    UserService userService;
    @Autowired
    ReviewServiceImpl reviewService;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    ProductRepository productRepository;

    /**
     * Method to create review for the products which is there in the database by the user who is logged in.
     * @param reviewRequestModel
     * @return ResponseEntity
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ReviewRest> createReview(@RequestBody ReviewRequestModel reviewRequestModel) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        if (productRepository.findById(reviewRequestModel.getProductId()).isEmpty())
        {
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());
        }
        else
        return new ResponseEntity(reviewService.createReviewByUserId(reviewRequestModel,user), HttpStatus.CREATED);
    }

    /**
     * Method to update the review done by the user.
     * @param reviewRequestModel
     * @return ResponseEntity
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ReviewRest> updateReview(@RequestBody ReviewRequestModel reviewRequestModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        if (reviewRepository.findById(reviewRequestModel.getProductId()).isEmpty())
        {
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());
        }
        else
        return new ResponseEntity(reviewService.updateReview(reviewRequestModel,user),HttpStatus.OK);


    }

    /**
     *
     * @param ProductId is of type long,contains unique long id generated for each product.
     * @return String
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping("/delete/{productId}")
    public String deleteReview(@PathVariable (value="productId") Long ProductId) throws Exception {
        if (ProductId == null) {
            throw new ClientSideException(Messages.NO_RECORD_FOUND.getMessage());
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            ReviewEntity reviewEntity = reviewService.deleteReviewByUser(user, ProductId);
             return Messages.DELETE_REVIEW.getMessage();
        }
    }

    /**
     * Method to display  all the reviews given to the productId given.
     * @param ProductId is of type long,contains unique long id generated for each product.
     * @return ReviewRest
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/fetch/{productId}")
    public List<ReviewRest> getReviewByProduct(@PathVariable (value="productId") Long ProductId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<ReviewRest> reviewRest=reviewService.findReviewByProductId(ProductId,user);
        return reviewRest;
    }

    /**
     * Method to display all the reviews done by the user.
     * @return
     * @throws ClientSideException throws custom exception
     */
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/fetch-user")
    public List<ReviewRest> getReviewByUserId() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<ReviewRest> reviewRest=reviewService.findReviewByUserId(user);
        return reviewRest;
    }
}
