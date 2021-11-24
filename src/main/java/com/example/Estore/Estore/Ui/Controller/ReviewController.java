package com.example.Estore.Estore.Ui.Controller;


import com.example.Estore.Estore.Services.ServiceImpl.ReviewServiceImpl;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.ReviewRequest.ReviewRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.ReviewRequest.ReviewRest;

import com.example.Estore.Estore.io.Entity.Review.ReviewEntity;

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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping
    public ResponseEntity<ReviewRest> createReview(@RequestBody ReviewRequestModel reviewRequestModel) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        return new ResponseEntity(reviewService.createReviewByUserId(reviewRequestModel,user), HttpStatus.CREATED);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path = "/update")
    public ResponseEntity<ReviewRest> updateReview(@RequestBody ReviewRequestModel reviewRequestModel){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        return new ResponseEntity(reviewService.updateReview(reviewRequestModel,user),HttpStatus.OK);


    }
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping("/delete/{productId}")
    public String deleteReview(@PathVariable (value="productId") Long ProductId) throws Exception {
        if (ProductId == null) {
            throw new Exception("product id not found");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            ReviewEntity reviewEntity = reviewService.deleteReviewByUser(user, ProductId);
            return "Successfully deleted";


        }
    }
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
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/fetch-user/{id}")
    public List<ReviewRest> getReviewByUserId(@PathVariable (value="id") Long id) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<ReviewRest> reviewRest=reviewService.findReviewByUserId(user,id);
        return reviewRest;
    }




}
