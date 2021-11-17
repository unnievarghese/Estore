package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Services.ServiceImpl.OrderServiceImpl;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
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

@RestController
@RequestMapping(path="/orders")


public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    String amount;

    @Transactional
    @Query(value = "SELECT orderAmount FROM orders ", nativeQuery = true)
    public double orderAmount() {
        return 0;
    }

    //http://localhost:8080/orders
    @PostMapping(path="/{cartId}")

    public OrderResponseModel createOrder(@PathVariable(value = "cartId") String cartId) throws Exception {

        if(cartId==null) {
            throw new Exception("Cart id null");
        }

        else{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());


        OrderEntity orderEntity = orderService.createOrder(user.getUserId());

        return new ModelMapper().map(orderEntity,OrderResponseModel.class);

    }
    }

//    // http://localhost:8080/orders/all
    @GetMapping(path = "/all")
    public List<OrderEntity> getLoggedInUserOrders() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());


        return (List<OrderEntity>) orderService.findAllOrders();

    }

    //http://localhost:8080/orders/1?status=Shipped
    @PutMapping(path = "/{orderId}")
    public OrderResponseModel updateOrderStatus(@PathVariable Long orderId, @RequestParam(value = "status") String status) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        orderService.updateOrderStatus(user,orderId,status);
        return null;

    }




    @DeleteMapping(path="{cartId}")
    public void deleteCart(@PathVariable(value = "cartId") String cartId) throws Exception{


        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //UserDto user = userService.getUser(auth.getName());

        orderService.deleteCart(cartId);
    }






    }















