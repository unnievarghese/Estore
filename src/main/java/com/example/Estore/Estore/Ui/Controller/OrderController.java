package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.OrderService;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.OrderRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderDetailsModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path="orders")


public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    UserService userService;

    //http://localhost:8080/orders
    @PostMapping
    public ResponseEntity<OrderResponseModel> createOrder(@RequestBody OrderRequestModel orderRequestModel, HttpServletRequest request) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        String url = orderService.getSiteURL(request);
        OrderResponseModel orderResponsemodel = orderService.createOrder(user.getId(), orderRequestModel, url);

        return new ResponseEntity<>(orderResponsemodel, HttpStatus.CREATED);

    }

//    // http://localhost:8080/orders/all
//    @GetMapping(path = "/all")
//    public ResponseEntity<List<OrderDetailsModel>> getLoggedInUserOrders() {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        List<OrderDetailsModel> orderDetailsModels = orderService.findAllOrders(user.getId());
//        if (orderDetailsModels.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } else {
//            return new ResponseEntity<>(orderDetailsModels, HttpStatus.OK);
//        }
//    }


//    //http://localhost:8080/orders/all/1
//    @GetMapping(path = "/all/{orderId}")
//    public ResponseEntity<OrderDetailsModel> getOrder(@PathVariable Long orderId) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        OrderDetailsModel orderDetailsModel = orderService.getOrderById(user.getId(),orderId);
//        return new ResponseEntity<>(orderDetailsModel, HttpStatus.OK);
//
//    }

//    //http://localhost:8080/orders/1?status=Shipped
//    @PutMapping(path = "/{orderId}")
//    public ResponseEntity<OrderResponseModel> updateOrderStatus(@PathVariable Long orderId, @RequestParam(value = "status") String status, HttpServletRequest request) throws Exception {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        String url = orderService.getSiteURL(request);
//        return new ResponseEntity<>(orderService.updateStatus(user, orderId, status, url), HttpStatus.ACCEPTED);
//    }


//    // http://localhost:8080/orders?status=Confirmed
//    @GetMapping
//    public ResponseEntity<List<OrderDetailsModel>> getOrderByStatus(@RequestParam(value = "status") String status) {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        List<OrderDetailsModel> orderDetailsModels = orderService.findOrderByStatus(user.getId(), status);
//        if (orderDetailsModels.isEmpty()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        } else {
//            return new ResponseEntity<>(orderDetailsModels, HttpStatus.OK);
//        }
//    }


//    // http://localhost:8080/orders/1
//    @DeleteMapping(path ="/{orderId}")
//    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId, HttpServletRequest request) throws Exception {
//
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        UserDto user = userService.getUser(auth.getName());
//
//        String url = orderService.getSiteURL(request);
//        orderService.cancelOrder(orderId, url, user.getId());
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//
//    }














    }
