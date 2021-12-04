package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Ui.Model.Request.UserRequest.UserLoginRequestModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This class is used to create a swagger endpoint for login.
 */
@RestController
public class AuthenticationController {

        @ApiOperation("User login")
        @ApiResponses(value = {
                @ApiResponse(code = 200,
                        message = "Response Headers",
                        responseHeaders = {
                                @ResponseHeader(
                                        name = "authorization",
                                        description = "Bearer <JWT value here>",
                                        response = String.class
                                ),
                                @ResponseHeader(
                                        name = "userId",
                                        description = "<Public user Id value here>",
                                        response = String.class
                                )
                        })
        })

        @PostMapping("/signin")
        public void theFakelogin(@RequestBody UserLoginRequestModel loginRequestModel){
            throw new IllegalStateException("This method should not be called.This method is implemented by spring security.");
        }
    }


