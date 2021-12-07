package com.example.Estore.Estore.Services.ServiceImpl;
import antlr.StringUtils;
import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.EmailService;
import com.example.Estore.Estore.Shared.dto.User.AddressDto;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponse.OrderResponseModel;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Order.OrderEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.AddressEntity;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
    public class OrderServiceImpl {

    /**
     * inject CartItemRepository dependency
     */
    @Autowired
    CartItemRepository cartItemRepository;

    /**
     * inject ProductRepository dependency
     */
    @Autowired
    ProductRepository productRepository;

    /**
     * inject OrderRepository dependency
     */
    @Autowired
    OrderRepository orderRepository;

    /**
     * inject UserRepository dependency
     */
    @Autowired
    UserRepository userRepository;

    /**
     * inject EmailService dependency
     */
    @Autowired
    EmailService emailService;


    /**
     * createOrder class implements the creation of order for all the products in the cart having open status
     * @param userId - for finding cart details and user details
     * @return orderEntity
     * @throws Exception - EMPTY_CART - no products in cart
     */
        public OrderEntity createOrder(String userId) throws Exception {

            //finding user with userid
            UserEntity userEntity = userRepository.findByUserId(userId);


            OrderEntity orderEntity = new OrderEntity();

           //setting the user address to order
            List<AddressEntity> addressEntities=userEntity.getAddress();
            if(addressEntities.isEmpty())
                throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
            orderEntity.setBillingAddress(userEntity.getAddress().get(0));
            orderEntity.setShippingAddress(userEntity.getAddress().get(1));

// if(addressEntity.equals(null))
//                    throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
//            orderEntity.setShippingAddress(userEntity.getAddress().get(1));
//            System.out.println("hello");
//            if(userEntity.getAddress().get(0).equals(false))
//                throw new ClientSideException(Messages.NO_BILLING.getMessage());
//            orderEntity.setBillingAddress(userEntity.getAddress().get(0));

           // orderEntity.setShippingAddress(userEntity.getAddress().get(1));

            //orderEntity.setBillingAddress(userEntity.getAddress().get(0));




            //setting status as confirmed
            orderEntity.setOrderStatus("confirmed");


            //finding total amount and setting
            List<CartItemEntity> cartItemEntity = cartItemRepository.findByCartStatus(userEntity);
            if(cartItemEntity.isEmpty())
                throw new ClientSideException(Messages.EMPTY_CART.getMessage());


            double totalamount=0;


            for(CartItemEntity item :cartItemEntity){


                //checking cart status
                if(item.isCartIsActive()) {


                    totalamount += item.getTotalPrice();
                    orderEntity.getCartitemEntityList().add(item);


                }
                item.setCartIsActive(false);//making cart status false


            }
            //setting total amount
            orderEntity.setOrderAmount(totalamount);

            //setting ordered date
            orderEntity.setOrderedTime(LocalDateTime.now());

            //setting user entity
            orderEntity.setUserEntity(userEntity);



            orderRepository.save(orderEntity);

            Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
            Long id=orderEntity.getOrderId();
            String status=orderEntity.getOrderStatus();
            double orderamount=orderEntity.getOrderAmount();
            //AddressEntity address=orderEntity.getShippingAddress();
            String address=orderEntity.getShippingAddress().getStreetName()+","+
                    orderEntity.getShippingAddress().getCity()+","+
                    orderEntity.getShippingAddress().getCountry()+","+
                    orderEntity.getShippingAddress().getPostalCode()+"<br>";

            String item="";
            for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
                item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
            }
            emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item));


            return orderEntity;

        }


    /**
     * createOrderByProductId implements creation of order from the open cart with only the product given by the user
     * @param productId - for creating order
     * @param userId - for finding cart details and user details
     * @return orderEntity
     * @throws Exception - INVALID_PRODUCTID - No products with productId
     */
    public OrderEntity createOrderByProductId(Long productId,String userId) throws Exception {
        //finding user with userid
       UserEntity userEntity = userRepository.findByUserId(userId);





        OrderEntity orderEntity = new OrderEntity();


        List<AddressEntity> addressEntities=userEntity.getAddress();
        if(addressEntities.isEmpty())
            throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
        orderEntity.setBillingAddress(userEntity.getAddress().get(0));
        orderEntity.setShippingAddress(userEntity.getAddress().get(1));








//        AddressEntity addressEntity=userEntity.getAddress().get(1);
//        if(addressEntity==null)
//            throw new ClientSideException(Messages.NO_SHIPPING.getMessage());
//        else{
//        //orderEntity.setShippingAddress(userEntity.getAddress().get(1));
//            }
//        System.out.println("hello4");
//
//        AddressEntity addressEntity1=userEntity.getAddress().get(0);
//        if(addressEntity1==null)
//            throw new ClientSideException(Messages.NO_BILLING.getMessage());
//        else{
//       // orderEntity.setBillingAddress(userEntity.getAddress().get(0));
//            }



        //setting status as confirmed
        orderEntity.setOrderStatus("confirmed");


        //finding total amount and setting
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity,productId);

        if (cartItemEntity==null)

            throw new ClientSideException(Messages.INVALID_PRODUCTID.getMessage());

        double totalamount=0;


            //checking cart status
        //if(cartItemEntity.isCartIsActive())

        //getting total amount
         totalamount=cartItemEntity.getTotalPrice();

        orderEntity.getCartitemEntityList().add(cartItemEntity);

        cartItemEntity.setCartIsActive(false);//making cart status false

        //setting total amount
        orderEntity.setOrderAmount(totalamount);

        //setting order date
        orderEntity.setOrderedTime(LocalDateTime.now());

        //setting user entity
        orderEntity.setUserEntity(userEntity);



        orderRepository.save(orderEntity);

        Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
        Long id=orderEntity.getOrderId();
        String status=orderEntity.getOrderStatus();
        double orderamount=orderEntity.getOrderAmount();
        //AddressEntity address=orderEntity.getShippingAddress();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item));


        return orderEntity;

    }


    /**
     * findByUserId class returns all order details of the particular user
     * @param id - for finding order details
     * @return orders
     * @throws Exception NO_ORDER - No order history with user
     */
    public List<OrderResponseModel> findByUserId(Long id) throws Exception  {

        //UserEntity userEntity = userRepository.findByUserId(id);

        List<OrderEntity>orderEntityList=orderRepository.findByuserId(id);
        if ((orderEntityList.isEmpty()))
        {
            throw new ClientSideException(Messages.NO_ORDER.getMessage());
        }
        else {
            List<OrderResponseModel>orders=new ArrayList<>();
            for (OrderEntity orderEntity:orderEntityList)
            {
                //OrderResponseModel order=new OrderResponseModel();
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
            }
            return  orders;
        }
    }


    /**
     * findByorderId returns all the order details of the particular user with the orderId
     * @param userId - for finding order details
     * @param id - for finding order details
     * @return orders
     * @throws Exception INVALID_ORDERID - No order wth given id
     */
    public OrderEntity findByorderId(Long userId, Long id) throws Exception  {

        //UserEntity userEntity = userRepository.findByUserId(userId);

        OrderEntity orderEntity=orderRepository.findByUserIdandOrderId(userId,id);

        if ((orderEntity==null))
        {
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());
        }
        else {
            //List<OrderResponseModel>orders=new ArrayList<>();
            //for (OrderEntity orderEntity:orderEntityList)
           // {
              return orderEntity;
            }


        }



    /**
     * findByorderStatus class returns all the order details of the particular user with the orderStatus
     * @param userId - for finding order details
     * @param id - for finding order details
     * @return orders
     * @throws Exception INVALID_STATUS - not valid status   NO_ORDER_STATUS - No order with status given
     */
    public List<OrderResponseModel> findByorderStatus(Long userId, String id) throws Exception  {
        String id1=id.toLowerCase();

        //System.out.println(id1);
        if (!id1.equals("confirmed") && !id1.equals("shipped") && !id1.equals("in-transit") && !id1.equals("delivered") && !id1.equals("cancelled"))
            throw new ClientSideException(Messages.INVALID_STATUS.getMessage());

        //UserEntity userEntity = userRepository.findByUserId(userId);

        List<OrderEntity>orderEntityList=orderRepository.findByOrderStatusandUserId(userId,id1);
        if ((orderEntityList.isEmpty()))
        {
            throw new ClientSideException(Messages.NO_ORDER_STATUS.getMessage());
        }
        else {
            List<OrderResponseModel>orders=new ArrayList<>();
            for (OrderEntity orderEntity:orderEntityList)
            {
                //OrderResponseModel order=new OrderResponseModel();
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
            }
            return  orders;
        }
    }


    /**
     *updateOrderStatus class implements the order status updation like shipped,in-transit,delivered by the seller only
     * @param userId-finding user details
     * @param orderId- finding order details
     * @param status - change status
     * @return orderEntity
     * @throws Exception INVALID_STATUS - not valid status  INVALID_ORDERID - No order with given Id   SAME_STATUS - already same status USER_CANCELLED - User cancelled the order
     */
    public OrderEntity updateOrderStatus(String userId, Long orderId, String status) throws Exception {

            String status1=status.toLowerCase();


        // if (!user.getUserId().equals("seller"))
        //    throw new IllegalStateException(orderId + "cannot update");
       // String status1 = status.toLowerCase();




        if (!status1.equals("shipped") && !status1.equals("in-transit") && !status1.equals("delivered"))
            throw new ClientSideException(Messages.INVALID_STATUS.getMessage());

        //finding order by orderid
        OrderEntity orderEntity=orderRepository.findByorderId(orderId);

        if (orderEntity==null)
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());

        if(orderEntity.getOrderStatus().equals("cancelled"))
            throw new ClientSideException(Messages.USER_CANCELLED.getMessage());

        if(orderEntity.getOrderStatus().equals(status1))
            throw new ClientSideException(Messages.SAME_STATUS.getMessage());

        //updating order status
        orderEntity.setOrderStatus(status1);
        orderRepository.save(orderEntity);

        Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
        Long id=orderEntity.getOrderId();
        String status2=orderEntity.getOrderStatus();
        double orderamount=orderEntity.getOrderAmount();
        //AddressEntity address=orderEntity.getShippingAddress();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        UserEntity userEntity = orderRepository.findByorderId(orderId).getUserEntity();
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status2,orderamount,address,item));



        return orderEntity;
    }


//    /**
//     * removeOrder class implements cancelling the order created by the user by passing orderId
//     * @param userId - for finding order details
//     * @param orderId - for finding order details
//     */

    /**
     * removeOrder class implements cancelling the order created by the user by passing orderId
     * @param userId for finding order details
     * @param orderId for finding order details
     * @throws Exception INVALID_ORDERID - No order with given orderId  ALREADY_CANCELLED - already cancelled order  CANCEL_REJECTED - Order no longer can be cancelled
     */
    public OrderEntity removeOrder(String userId,Long orderId) throws Exception{


        UserEntity userEntity = userRepository.findByUserId(userId);


        //UserEntity userEntity= new UserEntity();
            //BeanUtils.copyProperties(user,userEntity);

            //finding order by id
        OrderEntity orderEntity=orderRepository.findByUserIdandOrderId(userEntity.getId(), orderId);


        if (orderEntity==null)
                throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());
        if (orderEntity.getOrderStatus().equals("cancelled"))
                throw new ClientSideException(Messages.ALREADY_CANCELLED.getMessage());
        if (orderEntity.getOrderStatus().equals("delivered") || orderEntity.getOrderStatus().equals("in-transit"))
                throw new ClientSideException(Messages.CANCEL_REJECTED.getMessage());



        orderEntity.setOrderStatus("cancelled");

        for (CartItemEntity cartItemEntity : orderEntity.getCartitemEntityList()) {
                ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
                productEntity.setQuantity(productEntity.getQuantity() + cartItemEntity.getQuantity());
                productRepository.save(productEntity);
            }

        orderRepository.save(orderEntity);

        Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
        Long id=orderEntity.getOrderId();
        String status=orderEntity.getOrderStatus();
        double orderamount=orderEntity.getOrderAmount();
        //AddressEntity address=orderEntity.getShippingAddress();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item));



        return orderEntity;




        }



    }

























