package com.example.Estore.Estore.Services.ServiceImpl;
import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.EmailService;
import com.example.Estore.Estore.Ui.Model.Request.OrderRequest.OrderRequestModel;
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
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
     * reviewOrder class implements the review of order for all the products in the cart having open status
     * @param userId - for finding cart details and user details
     * @return orderEntity
     * @throws Exception - EMPTY_CART - no products in cart
     */
    public OrderEntity reviewOrder(String userId) throws Exception {


        //finding user with userid
        UserEntity userEntity = userRepository.findByUserId(userId);


        OrderEntity orderEntity = new OrderEntity();

        //finding the user address to order
        List<AddressEntity> addressEntities=userEntity.getAddress();
        if(addressEntities.isEmpty())
            throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
        orderEntity.setBillingAddress(userEntity.getAddress().get(0));
        orderEntity.setShippingAddress(userEntity.getAddress().get(1));

        //finding carts
        List<CartItemEntity> cartItemEntity = cartItemRepository.findByCartStatus(userEntity);
        if(cartItemEntity.isEmpty())
            throw new ClientSideException(Messages.EMPTY_CART.getMessage());

        double totalamount=0;
        int quantity=0;

        //finding amounts
        for(CartItemEntity item :cartItemEntity){
            totalamount += item.getTotalPrice();
            quantity+=item.getQuantity();
            orderEntity.getCartitemEntityList().add(item);
        }

        double delivery;
        if(quantity==1)
            delivery=40;
        else if(quantity>=2 && quantity<=4)
            delivery=100;
        else
            delivery=250;


        double tax=0.05*totalamount;

        orderEntity.setDeliverycharge(delivery);
        orderEntity.setTax(tax);
        orderEntity.setTotalItemAmount(totalamount);
        orderEntity.setOrderAmount(totalamount+delivery+tax);

        //finding card details
        if(userEntity.getCardDetails()==null)
            throw new ClientSideException(Messages.SET_CARD.getMessage());
        orderEntity.setCardEntity(userEntity.getCardDetails());

        //setting delivery date
        LocalDate date = LocalDate.now();
        orderEntity.setDeliveryDate(date.plusDays(7));

        return orderEntity;

    }




    /**
     * reviewOrder class implements the review of order for the particular products in the cart having open status
     * @param productId -use only particular product
     * @param userId - for finding cart details and user details
     * @return orderEntity
     * @throws Exception - INVALID_PRODUCTID - no products in cart with the id
     */
    public OrderEntity reviewOrder(String userId, Long productId) throws Exception {


        //finding user with userid
        UserEntity userEntity = userRepository.findByUserId(userId);


        OrderEntity orderEntity = new OrderEntity();

        //getting the user address to order
        List<AddressEntity> addressEntities=userEntity.getAddress();
        if(addressEntities.isEmpty())
            throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
        orderEntity.setBillingAddress(userEntity.getAddress().get(0));
        orderEntity.setShippingAddress(userEntity.getAddress().get(1));

        //finding cart items
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity,productId);
        if(cartItemEntity==null)
            throw new ClientSideException(Messages.INVALID_PRODUCTID.getMessage());

        //finding amounts
        double totalamount=cartItemEntity.getTotalPrice();
        int quantity;
        quantity=cartItemEntity.getQuantity();
        orderEntity.getCartitemEntityList().add(cartItemEntity);


        double delivery;
        if(quantity==1)
            delivery=40;
        else if(quantity>=2 && quantity<=4)
            delivery=100;
        else
            delivery=250;


        double tax=0.05*totalamount;

        orderEntity.setDeliverycharge(delivery);
        orderEntity.setTax(tax);
        orderEntity.setTotalItemAmount(totalamount);
        orderEntity.setOrderAmount(totalamount+delivery+tax);


        //finding card details
        if(userEntity.getCardDetails()==null)
            throw new ClientSideException(Messages.SET_CARD.getMessage());
        orderEntity.setCardEntity(userEntity.getCardDetails());

        //setting delivery date
        LocalDate date = LocalDate.now();
        orderEntity.setDeliveryDate(date.plusDays(7));

        return orderEntity;

    }





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

            //setting status as confirmed
            orderEntity.setOrderStatus("placed");

            //finding  carts to checkout
            List<CartItemEntity> cartItemEntity = cartItemRepository.findByCartStatus(userEntity);
            if(cartItemEntity.isEmpty())
                throw new ClientSideException(Messages.EMPTY_CART.getMessage());

            double totalamount=0;
            int quantity=0;

            //finding amounts and setting
            for(CartItemEntity item :cartItemEntity){
                totalamount += item.getTotalPrice();
                quantity+=item.getQuantity();
                orderEntity.getCartitemEntityList().add(item);
                item.setCartIsActive(false);//making cart status false
            }

            double delivery;
            if(quantity==1)
                delivery=40;
            else if(quantity>=2 && quantity<=4)
                delivery=100;
            else
                delivery=250;

            double tax=0.05*totalamount;

            orderEntity.setDeliverycharge(delivery);
            orderEntity.setTax(tax);
            orderEntity.setTotalItemAmount(totalamount);
            orderEntity.setOrderAmount(totalamount+delivery+tax);

            //setting ordered date
            orderEntity.setOrderedTime(LocalDateTime.now());

            //setting user entity
            orderEntity.setUserEntity(userEntity);

            orderEntity.setUserEntity1(userRepository.findByUserId("Cw4plwgIxr516rnVJ5MiGlQzbdSbaj"));

            //setting card details
            if(userEntity.getCardDetails()==null)
                throw new ClientSideException(Messages.SET_CARD.getMessage());
            orderEntity.setCardEntity(userEntity.getCardDetails());

            //setting delivery date
            LocalDate date = LocalDate.now();
            orderEntity.setDeliveryDate(date.plusDays(7));

            orderRepository.save(orderEntity);

            //Sending email
            Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
            Long id=orderEntity.getOrderId();
            String status=orderEntity.getOrderStatus();
            double orderamount=orderEntity.getOrderAmount();
            double tamount=orderEntity.getTotalItemAmount();
            double tax1=orderEntity.getTax();
            double dc=orderEntity.getDeliverycharge();
            LocalDate localDate=orderEntity.getDeliveryDate();
            String address=orderEntity.getShippingAddress().getStreetName()+","+
                    orderEntity.getShippingAddress().getCity()+","+
                    orderEntity.getShippingAddress().getCountry()+","+
                    orderEntity.getShippingAddress().getPostalCode()+"<br>";

            String item="";
            for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
                item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
            }
            emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item,tamount,tax1,dc,localDate));
            emailService.send(orderEntity.getUserEntity1().getEmail(),orderemailbuilder.buildorderplacedcontent(orderEntity.getUserEntity1().getFirstName(),id,status,orderamount,address,item,tamount,tax1,dc,localDate));

            return orderEntity;

        }


    /**
     * createOrderByProductId implements creation of order from the open cart with only the product given by the user
     * @param productId - for using particular product only
     * @param userId - for finding cart details and user details
     * @return orderEntity
     * @throws Exception - INVALID_PRODUCTID - No products with productId
     */
    public OrderEntity createOrderByProductId(Long productId,String userId) throws Exception {

        //finding user with userid
       UserEntity userEntity = userRepository.findByUserId(userId);

       OrderEntity orderEntity = new OrderEntity();

       //finding addresses from user
       List<AddressEntity> addressEntities=userEntity.getAddress();
       if(addressEntities.isEmpty())
            throw new ClientSideException(Messages.NO_ADDRESS.getMessage());
       orderEntity.setBillingAddress(userEntity.getAddress().get(0));
       orderEntity.setShippingAddress(userEntity.getAddress().get(1));


       //setting status as confirmed
       orderEntity.setOrderStatus("placed");

       //finding cart to checkout
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity,productId);

        if (cartItemEntity==null)
            throw new ClientSideException(Messages.INVALID_PRODUCTID.getMessage());

        //setting amounts
        double totalamount=cartItemEntity.getTotalPrice();
        int quantity;
        quantity=cartItemEntity.getQuantity();
        orderEntity.getCartitemEntityList().add(cartItemEntity);

        //making cart status false
        cartItemEntity.setCartIsActive(false);

        //setting order date
        double delivery;
        if(quantity==1)
            delivery=40;
        else if(quantity>=2 && quantity<=4)
            delivery=100;
        else
            delivery=250;

        double tax=0.05*totalamount;

        orderEntity.setDeliverycharge(delivery);
        orderEntity.setTax(tax);
        orderEntity.setTotalItemAmount(totalamount);
        orderEntity.setOrderAmount(totalamount+delivery+tax);
        orderEntity.setOrderedTime(LocalDateTime.now());

        //setting user entity
        orderEntity.setUserEntity(userEntity);

        orderEntity.setUserEntity1(userRepository.findByUserId("Cw4plwgIxr516rnVJ5MiGlQzbdSbaj"));

        //setting card details
        if(userEntity.getCardDetails()==null)
            throw new ClientSideException(Messages.SET_CARD.getMessage());
        orderEntity.setCardEntity(userEntity.getCardDetails());

        //setting delivery date
        LocalDate date=LocalDate.now();
        orderEntity.setDeliveryDate(date.plusDays(7));

        orderRepository.save(orderEntity);

        //sending email
        Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
        Long id=orderEntity.getOrderId();
        String status=orderEntity.getOrderStatus();
        double orderamount=orderEntity.getOrderAmount();
        double tamount=orderEntity.getTotalItemAmount();
        double tax1=orderEntity.getTax();
        double dc=orderEntity.getDeliverycharge();
        LocalDate localDate=orderEntity.getDeliveryDate();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item,tamount,tax1,dc,localDate));
        emailService.send(orderEntity.getUserEntity1().getEmail(),orderemailbuilder.buildorderplacedcontent(orderEntity.getUserEntity1().getFirstName(),id,status,orderamount,address,item,tamount,tax1,dc,localDate));

        return orderEntity;
    }


    /**
     * findByUserId class returns all order details of the particular user
     * @param id - for finding order details
     * @return returnValue
     * @throws Exception NO_ORDER - No order history with user
     */
    public List<OrderResponseModel> findByUserId(Long id) throws Exception  {


        List<OrderEntity>orderEntityList=orderRepository.findByuserId(id);
        List<OrderResponseModel> returnValue = new ArrayList<>();

        if ((orderEntityList.isEmpty()))
            throw new ClientSideException(Messages.NO_ORDER.getMessage());

        for (OrderEntity orderEntity:orderEntityList)
        {
            returnValue.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
        }
        return  returnValue;
    }



    /**
     * findByorderId returns the order details of user with the orderId
     * @param userId - for finding order details
     * @param id - for finding order details
     * @return orders
     * @throws Exception INVALID_ORDERID - No order wth given id
     */
    public OrderEntity findByorderId(Long userId, Long id) throws Exception  {


        OrderEntity orderEntity=orderRepository.findByUserIdandOrderId(userId,id);

        if ((orderEntity==null))
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());
        return orderEntity;


    }


    /**
     * findByorderStatus class returns order details of user with the orderStatus
     * @param userId - for finding order details
     * @param id - for finding order details
     * @return orders
     * @throws Exception INVALID_STATUS - not valid status   NO_ORDER_STATUS - No order with status given
     */
    public List<OrderResponseModel> findByorderStatus(Long userId, String id) throws Exception  {
        String id1=id.toLowerCase();

        if (!id1.equals("confirmed") && !id1.equals("shipped") && !id1.equals("delivered") && !id1.equals("usercancelled")
                && !id1.equals("sellercancelled") && !id1.equals("placed"))
            throw new ClientSideException(Messages.INVALID_STATUS.getMessage());

        List<OrderEntity>orderEntityList=orderRepository.findByOrderStatusandUserId(userId,id1);
        if ((orderEntityList.isEmpty()))
            throw new ClientSideException(Messages.NO_ORDER_STATUS.getMessage());

        List<OrderResponseModel>orders=new ArrayList<>();
        for (OrderEntity orderEntity:orderEntityList)
        {
                orders.add(new ModelMapper().map(orderEntity,OrderResponseModel.class));
        }
        return  orders;

    }


    /**
     * getOrderBySeller returns the order details part of seller only with the orderId
     * @param sid - seller ID
     * @param id - for finding order details
     * @return orders
     * @throws Exception INVALID_ORDERID - No order wth given id
     */
    public OrderEntity getOrdersBySeller(Long sid,Long id) throws Exception {

        OrderEntity orderEntity=orderRepository.findBySellerIdandOrderId(sid,id);

        if (orderEntity==null)
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());
        return orderEntity;

    }




    /**
     *updateOrderStatus class implements the order status updation like confirmed,shipped,delivered,sellercancelled by the seller only
     * @param userId-finding user details
     * @param orderId- finding order details
     * @param status - change status
     * @return orderEntity
     * @throws Exception INVALID_STATUS - not valid status  INVALID_ORDERID - No order with given Id   SAME_STATUS - already same status USER_CANCELLED - User cancelled the order
     */
    public OrderEntity updateOrderStatus(Long userId, Long orderId, String status) throws Exception {

            String status1=status.toLowerCase();

        if (!status1.equals("shipped") && !status1.equals("confirmed") && !status1.equals("delivered") && !status1.equals("sellercancelled"))
            throw new ClientSideException(Messages.INVALID_STATUS1.getMessage());

        //finding order by orderid
        OrderEntity orderEntity=orderRepository.findBySellerIdandOrderId(userId,orderId);

        if (orderEntity==null)
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());

        if(orderEntity.getOrderStatus().equals("sellercancelled"))
            throw new ClientSideException(Messages.ALREADY_CANCELLED2.getMessage());

        if(orderEntity.getOrderStatus().equals("usercancelled"))
            throw new ClientSideException(Messages.USER_CANCELLED.getMessage());

        //if(orderEntity.getOrderStatus().equals("delivered"))
         //   throw new ClientSideException(Messages.CANCEL_REJECTED2.getMessage());

        if(orderEntity.getOrderStatus().equals(status1))
            throw new ClientSideException(Messages.SAME_STATUS.getMessage());

        String currentstatus=orderEntity.getOrderStatus();
        //updating order status

        if(status1.equals("sellercancelled")) {
            if(currentstatus.equals("placed") || currentstatus.equals("confirmed") || currentstatus.equals("shipped"))
            {orderEntity.setOrderStatus(status1);
             orderEntity.setDeliveryDate(null);
            //updating product
             for (CartItemEntity cartItemEntity : orderEntity.getCartitemEntityList()) {
                ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
                productEntity.setQuantity(productEntity.getQuantity() + cartItemEntity.getQuantity());
                productRepository.save(productEntity);
            }orderRepository.save(orderEntity);}
            else{
                throw new ClientSideException(Messages.CANCEL_REJECTED.getMessage());
            }
        }
        else if(status1.equals("delivered")){
            if(currentstatus.equals("shipped"))
            { orderEntity.setOrderStatus(status1);
                orderEntity.setDeliveryDate(LocalDate.now());
                orderRepository.save(orderEntity);}
            else{
                throw new ClientSideException(Messages.CANCEL_REJECTED.getMessage());
            }
        }

        else if(status1.equals("shipped")){
            if(currentstatus.equals("confirmed"))
            { orderEntity.setOrderStatus(status1);
                orderRepository.save(orderEntity);}
            else{
                throw new ClientSideException(Messages.CANCEL_REJECTED.getMessage());
            }
        }


        else {

            orderEntity.setOrderStatus(status1);
            orderRepository.save(orderEntity);
        }

        Orderemailbuilder orderemailbuilder =new Orderemailbuilder();
        Long id=orderEntity.getOrderId();
        String status2=orderEntity.getOrderStatus();
        double orderamount=orderEntity.getOrderAmount();
        double tamount=orderEntity.getTotalItemAmount();
        double tax1=orderEntity.getTax();
        double dc=orderEntity.getDeliverycharge();
        LocalDate localDate=orderEntity.getDeliveryDate();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        UserEntity userEntity = orderRepository.findByorderId(orderId).getUserEntity();
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status2,orderamount,address,item,tamount,tax1,dc,localDate));

        return orderEntity;
    }



    /**
     * removeOrder class implements cancelling the order created by the user by passing orderId
     * @param userId for finding order details
     * @param orderId for finding order details
     * @param orderRequestModel give reason and rating
     * @return orderEntity
     * @throws Exception INVALID_ORDERID - No order with given orderId  ALREADY_CANCELLED - already cancelled order  CANCEL_REJECTED - Order no longer can be cancelled
     */
    public OrderEntity removeOrder(String userId,Long orderId,OrderRequestModel orderRequestModel) throws Exception{


        UserEntity userEntity = userRepository.findByUserId(userId);


        //finding order by id
        OrderEntity orderEntity=orderRepository.findByUserIdandOrderId(userEntity.getId(), orderId);


        if (orderEntity==null)
                throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());
        if (orderEntity.getOrderStatus().equals("usercancelled"))
                throw new ClientSideException(Messages.ALREADY_CANCELLED1.getMessage());
        if (orderEntity.getOrderStatus().equals("shipped"))
                throw new ClientSideException(Messages.CANCEL_REJECTED1.getMessage());
        if (orderEntity.getOrderStatus().equals("delivered"))
                throw new ClientSideException(Messages.CANCEL_REJECTED2.getMessage());



        orderEntity.setOrderStatus("usercancelled");
        orderEntity.setDeliveryDate(null);

        //get reason and rating
        orderEntity.setOrderrating(orderRequestModel.getRating());
        orderEntity.setReason(orderRequestModel.getReason());

        //upating product
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
        double tamount=orderEntity.getTotalItemAmount();
        double tax1=orderEntity.getTax();
        double dc=orderEntity.getDeliverycharge();
        LocalDate localDate=orderEntity.getDeliveryDate();
        String address=orderEntity.getShippingAddress().getStreetName()+","+
                orderEntity.getShippingAddress().getCity()+","+
                orderEntity.getShippingAddress().getCountry()+","+
                orderEntity.getShippingAddress().getPostalCode()+"<br>";

        String item="";
        for(CartItemEntity cartItem:orderEntity.getCartitemEntityList()){
            item+="Product name : "+cartItem.getProductName()+"  Quantity : "+cartItem.getQuantity()+" Total Price : "+cartItem.getTotalPrice()+"<br>";
        }
        emailService.send(userEntity.getEmail(),orderemailbuilder.buildorderplacedcontent(userEntity.getFirstName(),id,status,orderamount,address,item,tamount,tax1,dc,localDate));

        return orderEntity;


        }

    /**
     * returnOrder class implements returning the order created by the user by passing orderId
     * @param userId-for finding order details
     * @param id- for finding order details
     * @param orderRequestModel- give reason and rating
     * @return orderEntity
     * @throws Exception INVALID_ORDERID - No order with given orderId NOT-DELIVERED - order not delivered yet ORDER_CANCELLED-order already cancelled
     */

    public OrderEntity returnByOrderId(String userId, Long id, OrderRequestModel orderRequestModel) throws Exception  {

        UserEntity userEntity=userRepository.findByUserId(userId);
        OrderEntity orderEntity=orderRepository.findByUserIdandOrderId(userEntity.getId(),id);

        if (orderEntity==null)
            throw new ClientSideException(Messages.INVALID_ORDERID.getMessage());

        if(orderEntity.getOrderStatus().equals("usercancelled") || orderEntity.getOrderStatus().equals("sellercancelled"))
            throw new ClientSideException(Messages.ORDER_CANCELLED.getMessage());

        if(!orderEntity.getOrderStatus().equals("delivered"))
            throw new ClientSideException(Messages.NOT_DELIVERED.getMessage());

        if(orderEntity.getOrderStatus().equals("returned"))
            throw new ClientSideException(Messages.ALREADY_RETURNED.getMessage());

        LocalDateTime localDate1=orderEntity.getUpdatedTime();
        LocalDateTime localDate2=LocalDateTime.now();
        long seconds = ChronoUnit.SECONDS.between(localDate1, localDate2);
        //long days = ChronoUnit.DAYS.between(localDate1, localDate2);

        //return policy
        if(seconds>=120)
            throw new ClientSideException(Messages.CANT_RETURN.getMessage());


        else {
            orderEntity.setOrderStatus("returned");
            //reason and rating
            orderEntity.setOrderrating(orderRequestModel.getRating());
            orderEntity.setReason(orderRequestModel.getReason());
            //updating product
            for (CartItemEntity cartItemEntity : orderEntity.getCartitemEntityList()) {
                ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
                productEntity.setQuantity(productEntity.getQuantity() + cartItemEntity.getQuantity());
                productRepository.save(productEntity);
            }
            orderRepository.save(orderEntity);
            return orderEntity;
        }


    }


}

























