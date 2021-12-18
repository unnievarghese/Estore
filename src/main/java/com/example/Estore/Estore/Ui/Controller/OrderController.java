package com.example.Estore.Estore.Ui.Controller;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ServiceImpl.OrderServiceImpl;
import com.example.Estore.Estore.Services.UserService;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.OrderRequest.OrderRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderProcessModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.CardRest;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/orders")


public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    UserService userService;




    /**
     *reviewOrder class implements review of order before placing order for all the products in the cart having open status
     * @return orderEntity
     * @throws ClientSideException Throws custom exceptions.
     */

    //http://localhost:8080/estore/orders/review

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path = "/review")
    public OrderProcessModel reviewOrder() throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderProcessModel orderEntity = orderService.reviewOrder(user.getUserId());
        return orderEntity;

    }



    /**
     *reviewOrderByproduct class implements the review of order before placing order for only the particular products in the cart having open status
     * @param productId - review with particular product only
     * @return orderEntity
     * @throws ClientSideException Throws custom exceptions.
     */

    //http://localhost:8080/estore/orders/review/productId

    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PostMapping(path ="/review/{productId}")
    public OrderProcessModel reviewOrderByproduct(@PathVariable (value="productId")Long productId) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderProcessModel orderEntity = orderService.reviewOrder(user.getUserId(),productId);
        return orderEntity;


    }

    /**
     *createOrder class implements the creation of order for all the products in the cart having open status
     * @return orderEntity
     * @throws ClientSideException Throws custom exceptions.
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
        OrderResponseModel orderEntity = orderService.createOrder(user.getUserId());
        return orderEntity;
    }


    /**
     *createOrderByProductId implements creation of order from the open cart with only the product given by the user
     * @param productId - - create order with particular product only
     * @return orderEntity
     * @throws ClientSideException Throws custom exceptions.
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
        OrderResponseModel orderEntity = orderService.createOrderByProductId(productId,user.getUserId());
        return orderEntity;
    }




    /**
     *getOrderByUser class returns all order details of the particular user
     * @return order
     * @throws ClientSideException Throws custom exceptions.
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
        return orderService.findByUserId(user.getId());

    }


    /**
     *getOrderByorderId class returns the order details of the user with the orderId
     * @param orderId - passed for getting order details
     * @return order
     * @throws ClientSideException Throws custom exceptions.
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
        OrderResponseModel order=orderService.findByorderId(user.getId(),orderId);
        return order;
    }


    /**
     *getOrderByStatus class returns  order details of user with the orderStatus
     * @param orderStatus - passed for getting order details
     * @return order
     * @throws ClientSideException Throws custom exceptions.
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
        return orderService.findByorderStatus(user.getId(),orderStatus);
    }


    /**
     *getOrderBySeller class returns the order details part of seller only  with the orderId
     * @param orderId - passed for getting order details
     * @return order
     * @throws ClientSideException Throws custom exceptions.
     */
//    http://localhost:8080/estore/orders/seller/1
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @GetMapping(path="/seller/{orderId}")
    public OrderResponseModel getOrderBySeller(@PathVariable (value="orderId") Long orderId) throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderResponseModel order = orderService.getOrdersBySeller(user.getId(),orderId);
        return order;


    }


    /**
     *updateOrderStatus class implements the order status updation like confirmed,shipped,delivered,sellercancelled by the seller only
     * @param orderId - passing orderId for changing status
     * @param status -  passing the new status
     * @return returns orderEntity
     * @throws ClientSideException Throws custom exceptions.
     */
    //http://localhost:8080/estore/orders/seller/1?status=Shipped
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @Secured("ROLE_SELLER")
    @PutMapping(path = "seller/{orderId}")
    public OrderResponseModel updateOrderStatus(@PathVariable Long orderId, @RequestParam(value = "status") String status) throws Exception {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderResponseModel orderEntity=orderService.updateOrderStatus(user.getId(),orderId,status);
        return orderEntity;


    }


    /**
     *cancelByorderId class implements cancelling the order created by the user by giving orderId
     * @param orderId - passed for cancelling order
     * @return orderEntity
     * @throws ClientSideException Throws custom exceptions.
     */

    //http://localhost:8080/estore/orders/cancel/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @DeleteMapping(path="/cancel/{orderId}")
    public OrderResponseModel cancelByOrderId(@PathVariable(value = "orderId") Long orderId,@RequestBody OrderRequestModel orderRequestModel) throws Exception{


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderResponseModel orderEntity=orderService.removeOrder(user.getUserId(), orderId,orderRequestModel);
        return orderEntity;

    }


    /**
     *returnByorderId class return back the partcular order by passing the orderId
     * @param orderId - passed for returning order
     * @return order
     * @throws ClientSideException Throws custom exceptions.
     */
    //http://localhost:8080/estore/orders/return/orderId
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization",
                    value = "${userController.authorizationHeader.description}",
                    paramType = "header")
    })
    @PutMapping(path = "/return/{orderId}")
    public OrderResponseModel returnByOrderId(@PathVariable (value="orderId") Long orderId, @RequestBody OrderRequestModel orderRequestModel) throws Exception {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDto user = userService.getUser(auth.getName());
        OrderResponseModel order=orderService.returnByOrderId(user.getUserId(),orderId,orderRequestModel);
        return order;
    }



}

