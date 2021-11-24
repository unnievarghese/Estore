package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.ServiceImpl.OrderServiceImpl;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.OperationStatusModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationName;
import com.example.Estore.Estore.Ui.Model.Response.RequestOperationStatus;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/orders")


public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    //http://localhost:8080/estore/orders/create
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/create")
    public OrderResponseModel createOrder() throws Exception {



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());


        OrderEntity orderEntity = orderService.createOrder(user.getUserId());

        return new ModelMapper().map(orderEntity,OrderResponseModel.class);


    }

    //http://localhost:8080/estore/orders/create/productId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/create/{productId}")
    public OrderResponseModel createOrderByProductId(@PathVariable Long productId) throws Exception {



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());


        OrderEntity orderEntity = orderService.createOrderByProductId(productId,user.getUserId());

        return new ModelMapper().map(orderEntity,OrderResponseModel.class);


    }





     //http://localhost:8080/orders/all


//    @GetMapping
//    public Optional<OrderEntity> findByuserId() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//        System.out.println(user.getId());
//        Optional<OrderEntity> orderEntity = orderService.findByuserId(user.getId());
//        return orderEntity;
//
//    }


    //http://localhost:8080/estore/orders
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping
    public List<OrderResponseModel> getOrderByUser() throws Exception{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<OrderResponseModel> order=orderService.findByUserId(user,user.getId());
        return order;
    }

    //http://localhost:8080/estore/orders/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/{orderId}")
    public List<OrderResponseModel> getOrderByorderId(@PathVariable (value="orderId") Long orderId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<OrderResponseModel> order=orderService.findByorderId(user,orderId);
        return order;
    }

    //http://localhost:8080/estore/orders/filter?status=shipped
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/filter")
    public List<OrderResponseModel> getOrderByStatus(@RequestParam (value="status") String orderStatus) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        List<OrderResponseModel> order=orderService.findByorderStatus(user,orderStatus);
        return order;
    }









    //http://localhost:8080/estore/orders/1?status=Shipped
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path = "/{orderId}")
    public OrderResponseModel updateOrderStatus(@PathVariable Long orderId, @RequestParam(value = "status") String status) throws Exception {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        OrderEntity orderEntity=orderService.updateOrderStatus(user,orderId,status);
        return new ModelMapper().map(orderEntity,OrderResponseModel.class);


    }







    //http://localhost:8080/estore/orders/cancel/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path="/cancel/{orderId}")
    public void cancelByorderId(@PathVariable(value = "orderId") Long orderId) throws Exception{


        if (orderId == null) {
            throw new Exception("Record with given " + orderId + " is not found");
        }
        else {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            orderService.removeOrder(user, orderId);


        }
    }






    }















