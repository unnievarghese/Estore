package com.example.Estore.Estore.Services.ServiceImpl;
import com.example.Estore.Estore.Shared.dto.Order.OrderDto;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Repositories.Cart.CartItemRepository;
import com.example.Estore.Estore.io.Repositories.Order.OrderRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.AddressRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
    public class OrderServiceImpl {

        @Autowired
    CartItemRepository cartItemRepository;

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

            //finding user with userid
            UserEntity userEntity = userRepository.findByUserId(userId);


            OrderEntity orderEntity = new OrderEntity();

            //setting the user address to order
            orderEntity.setShippingAddress(userEntity.getAddress().get(0));



            //setting status as confirmed
            orderEntity.setOrderStatus("confirmed");


            //finding total amount and setting
            List<CartItemEntity> cartItemEntity = cartItemRepository.findByUserEntity(userEntity);
            double totalamount=0;

            for(CartItemEntity item :cartItemEntity){

                //checking cart status
                if(item.isCartIsActive())
                 totalamount+=item.getTotalPrice();
                item.setCartIsActive(false);//making cart status false

            }
            orderEntity.setOrderAmount(totalamount);
            orderEntity.setUserEntity(userEntity);



            //cartEntity.setCartStatus("closed");
            orderRepository.save(orderEntity);
            return orderEntity;

        }

    public OrderEntity createOrderByProductId(Long productId,String userId) throws Exception {

        //finding user with userid
       UserEntity userEntity = userRepository.findByUserId(userId);




        OrderEntity orderEntity = new OrderEntity();

        //setting the user address to order
        orderEntity.setShippingAddress(userEntity.getAddress().get(0));



        //setting status as confirmed
        orderEntity.setOrderStatus("confirmed");

        //finding total amount and setting
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity,productId);
        double totalamount=0;

        //for(CartItemEntity item :cartItemEntity){

            //checking cart status
        //if(cartItemEntity.isCartIsActive())
         totalamount=cartItemEntity.getTotalPrice();
        cartItemEntity.setCartIsActive(false);//making cart status false


        orderEntity.setOrderAmount(totalamount);



        //cartEntity.setCartStatus("closed");
        orderRepository.save(orderEntity);
        return orderEntity;

    }






//    public List<OrderEntity> findByuserId(Long userId) {
//        //UserEntity userEntity = new UserEntity();
//       // BeanUtils.copyProperties(user, userEntity);
//      List<OrderEntity> orderEntity =orderRepository.findByuserId(userId);
//      return orderEntity;
//
//       // return orderRepository.findByuserId(userId);
//    }


    public List<OrderResponseModel> findByUserId(UserDto user, Long id) throws Exception  {

        //UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        List<OrderEntity>orderEntityList=orderRepository.findByuserId(id);
        if ((orderEntityList.isEmpty()))
        {
            throw new Exception("no orders by the user");
        }
        else {
            List<OrderResponseModel>orders=new ArrayList<>();
            for (OrderEntity orderEntity:orderEntityList)
            {
                OrderResponseModel order=new OrderResponseModel();
               // orderEntity.setShippingAddress(userEntity.getAddress().get(0));

               // order.setShippingAddress(orderEntity.getShippingAddress());
                //BeanUtils.copyProperties(orderEntity,order);
               // orders.add(order);
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
            }
            return  orders;
        }
    }


    public List<OrderResponseModel> findByorderId(UserDto user, Long id) throws Exception  {

        //UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        List<OrderEntity>orderEntityList=orderRepository.findByOrderId(id);
        if ((orderEntityList.isEmpty()))
        {
            throw new Exception("no orders by the orderId");
        }
        else {
            List<OrderResponseModel>orders=new ArrayList<>();
            for (OrderEntity orderEntity:orderEntityList)
            {
                OrderResponseModel order=new OrderResponseModel();
                // orderEntity.setShippingAddress(userEntity.getAddress().get(0));

                // order.setShippingAddress(orderEntity.getShippingAddress());
                //BeanUtils.copyProperties(orderEntity,order);
                // orders.add(order);
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
            }
            return  orders;
        }
    }


    public List<OrderResponseModel> findByorderStatus(UserDto user, String id) throws Exception  {
        String id1=id.toLowerCase();

        System.out.println(id1);
        //UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        List<OrderEntity>orderEntityList=orderRepository.findByOrderStatusandUserId(user.getId(),id1);
        if ((orderEntityList.isEmpty()))
        {
            throw new Exception("no status by the given status");
        }
        else {
            List<OrderResponseModel>orders=new ArrayList<>();
            for (OrderEntity orderEntity:orderEntityList)
            {
                OrderResponseModel order=new OrderResponseModel();
                // orderEntity.setShippingAddress(userEntity.getAddress().get(0));

                // order.setShippingAddress(orderEntity.getShippingAddress());
                //BeanUtils.copyProperties(orderEntity,order);
                // orders.add(order);
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
            }
            return  orders;
        }
    }





    public OrderEntity updateOrderStatus(UserDto user, Long orderId, String status) throws Exception {

            String status1=status.toLowerCase();

        //UserEntity userEntity = userRepository.findByUserId(userId);


        // if (!user.getUserId().equals("seller"))
        //    throw new IllegalStateException(orderId + "cannot update");
       // String status1 = status.toLowerCase();
//        if (!status1.equals("shipped") && !status1.equals("in-transit") && !status1.equals("delivered"))
//            throw new IllegalStateException("Invalid update");

        //UserEntity userEntity= new UserEntity();
        //BeanUtils.copyProperties(user,userEntity);

        System.out.println(status1);
        //finding order by orderid
        OrderEntity orderEntity=orderRepository.findByorderId(orderId);

        if(orderEntity.getOrderStatus().equals(status1))
            throw new IllegalStateException("The order is already with the same status");

        //updating order status
        orderEntity.setOrderStatus(status1);
        orderRepository.save(orderEntity);
        return orderEntity;


    }





        public void removeOrder(UserDto user,Long orderId){

            UserEntity userEntity= new UserEntity();
            BeanUtils.copyProperties(user,userEntity);

            //finding order by id
            OrderEntity orderEntity=orderRepository.findByorderId(orderId);

            if(orderEntity.getOrderStatus().equals("delivered")) //|| orderEntity.getOrderStatus().equals("in-transit") || orderEntity.getOrderStatus().equals("delivered"))
                throw new IllegalStateException("Already delivered");

            else {

                orderEntity.setOrderStatus("cancelled");
                orderRepository.save(orderEntity);


                //orderRepository.deleteOrder(orderId);
            }


        }



    }

























