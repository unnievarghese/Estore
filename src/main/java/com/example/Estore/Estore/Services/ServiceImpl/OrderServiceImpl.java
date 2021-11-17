package com.example.Estore.Estore.Services.ServiceImpl;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.Order.OrderRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.AddressRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
    public class OrderServiceImpl {

        @Autowired
        ProductRepository productRepository;

        @Autowired
        OrderRepository orderRepository;

        @Autowired
        AddressRepository addressRepository;

//        @Autowired
//        CartRepository cartRepository;

        @Autowired
        UserRepository userRepository;


        public OrderEntity createOrder(String userId) throws Exception {

            UserEntity userEntity = userRepository.findByUserId(userId);


            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setShippingAddress(userEntity.getAddress().get(0));
            orderEntity.setBillingAddress(userEntity.getAddress().get(0));
            System.out.println("Order Successfully Placed");

            orderEntity.setOrderStatus("confirmed");
            orderEntity.setOrderId("G5P5A");
            //orderRepository.findbyOrderAmount();



            orderEntity.setOrderAmount(1000);
            //cartentity.getquantity * rtemtity.getpricefor
            //for cartentity

            //cartEntity.setCartStatus("closed");
            orderRepository.save(orderEntity);
            return orderEntity;

        }




        public Iterable<OrderEntity> findAllOrders() {



            //cartEntity.setCartStatus("closed");
            return orderRepository.findAll();

        }

    public OrderEntity updateOrderStatus(UserDto user, Long orderId, String status) throws Exception {

        if (!user.getUserId().equals("seller"))
            throw new IllegalStateException(orderId + "cannot update");

            String status1 = status.toLowerCase();
        if (!status1.equals("shipped") && !status1.equals("in-transit") && !status1.equals("delivered"))
            throw new IllegalStateException("Invalid update");

        OrderResponseModel orderResponsemodel = new OrderResponseModel();

        Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
        if (orderEntity.isEmpty())
            throw new IllegalStateException("Invalid orderId");

            if (orderEntity.get().getOrderStatus().equals(status1))
            throw new IllegalStateException("same status");

        OrderEntity orderEntity1 = orderEntity.get();
        orderEntity1.setOrderStatus(status1);

        OrderEntity orderEntity2 = orderRepository.save(orderEntity1);

//        BeanUtils.copyProperties(orderEntity2, orderResponsemodel);
//        AddressResponseModel addressResponseModel = new AddressResponseModel();
//        BeanUtils.copyProperties(orderEntity2.getShippingAddress(), addressResponseModel);
//        orderResponsemodel.setShippingAddress(addressResponseModel);
//        return orderResponsemodel;
        return null;
    }

        public void deleteCart(String orderId){

           // boolean exists=orderRepository.existsById(orderId);
           // if(!exists)
                //throw new IllegalStateException(orderId + "does not exist");
            orderRepository.deleteByOrderId(orderId);
            //assertThat(deleteRecord).isEqualTo(i);
        }



    }

























