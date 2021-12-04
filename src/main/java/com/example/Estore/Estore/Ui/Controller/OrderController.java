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
import org.springframework.security.access.annotation.Secured;
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


    /**
     *createOrder class implements the creation of order for all the products in the cart having open status
     * @return orderEntity
     * @throws Exception
     */
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


    /**
     *createOrderByProductId implements creation of order from the open cart with only the product given by the user
     * @param productId - user passes the productId to create order
     * @return orderEntity
     * @throws Exception
     */
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


    /**
     *getOrderByUser class returns all order details of the particular user
     * @return order
     * @throws Exception
     */
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
        List<OrderResponseModel> order=orderService.findByUserId(user.getId());
        return order;
    }


    /**
     *getOrderByorderId class returns all the order details of the particular user with the orderId
     * @param orderId - passed for getting order details
     * @return order
     * @throws Exception
     */
    //http://localhost:8080/estore/orders/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @GetMapping(path = "/{orderId}")
    public OrderResponseModel getOrderByOrderId(@PathVariable (value="orderId") Long orderId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

//        OrderEntity orderEntity = orderService.createOrderByProductId(productId,user.getUserId());
//
//        return new ModelMapper().map(orderEntity,OrderResponseModel.class);

        OrderEntity order=orderService.findByorderId(user.getId(),orderId);
        return new ModelMapper().map(order,OrderResponseModel.class);
    }


    /**
     *getOrderByStatus class returns all the order details of the particular user with the orderStatus
     * @param orderStatus - passed for getting order details
     * @return order
     * @throws Exception
     */
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
        List<OrderResponseModel> order=orderService.findByorderStatus(user.getId(),orderStatus);
        return order;
    }


    /**
     *updateOrderStatus class implements the order status updation like shipped,in-transit,delivered by the seller only
     * @param orderId - passing orderId for changing status
     * @param status -  passing the new status
     * @return returns orderEntity
     * @throws Exception
     */
    //http://localhost:8080/estore/orders/1?status=Shipped
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @PutMapping(path = "/{orderId}")
    public OrderResponseModel updateOrderStatus(@PathVariable Long orderId, @RequestParam(value = "status") String status) throws Exception {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());

        OrderEntity orderEntity=orderService.updateOrderStatus(user.getUserId(),orderId,status);
        return new ModelMapper().map(orderEntity,OrderResponseModel.class);


    }


    /**
     *cancelByorderId class implements cancelling the order created by the user by giving orderId
     * @param orderId - passed for cancelling order
     * @throws Exception
     */

    //http://localhost:8080/estore/orders/cancel/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path="/cancel/{orderId}")
    public OrderResponseModel cancelByOrderId(@PathVariable(value = "orderId") Long orderId) throws Exception{


            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            UserDto user = userService.getUser(auth.getName());
            OrderEntity orderEntity=orderService.removeOrder(user.getUserId(), orderId);
            return new ModelMapper().map(orderEntity,OrderResponseModel.class);




    }


    }






















