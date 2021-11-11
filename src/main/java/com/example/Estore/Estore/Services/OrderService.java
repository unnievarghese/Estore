package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Request.OrderRequestModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderDetailsModel;
import com.example.Estore.Estore.Ui.Model.Response.OrderResponseModel;
import com.example.Estore.Estore.Ui.Model.Response.UserRequest.AddressesRest;
import com.example.Estore.Estore.io.Entity.OrderEntity;
import com.example.Estore.Estore.io.Entity.User.AddressEntity;
import com.example.Estore.Estore.io.Repositories.OrderRepository;
import com.example.Estore.Estore.io.Repositories.User.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

    @Service
    public class OrderService {

        @Autowired
        ProductRepository productRepository;

        @Autowired
        OrderRepository orderRepository;

        @Autowired
        AddressRepository addressRepository;

        @Autowired
        CartRepository cartRepository;

//        @Autowired
//        EmailService emailService;

        public OrderResponseModel createOrder(Long userId, OrderRequestModel orderRequestModel, String url) throws Exception {

            CartEntity cartEntity = findCartByUserId(userId);

            if (cartEntity.getCartItemEntityList().isEmpty())
                throw new RuntimeException("No products in cart");

            for (CartItemEntity cartItemEntity : cartEntity.getCartItemEntityList()) {
                if (cartItemEntity.getProductEntity().getQuantity() < cartItemEntity.getQuantity()) {
                    throw new RuntimeException("Required quantity not available. Please update Cart Quantity/ Please wait for the seller to add products");
                }
            }

            AddressEntity shippingEntity = findAddressById(orderRequestModel.getShippingAddress());
            AddressEntity billingEntity = findAddressById(orderRequestModel.getBillingAddress());

            if ((shippingEntity.getUserDetails().getId() != userId) || (billingEntity.getUserDetails().getId() != userId))
                throw new RuntimeException("this is not user address");

            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setShippingAddress(shippingEntity);
            orderEntity.setBillingAddress(billingEntity);
            orderEntity.setOrderStatus("confirmed");

            orderEntity.setOrderAmount(cartEntity.getTotalAmount());

            cartEntity.setCartStatus("closed");
            CartEntity storedCartEntity = cartRepository.save(cartEntity);

            orderEntity.setCartEntity(storedCartEntity);

            OrderEntity storedEntity = orderRepository.save(orderEntity);

            for (CartItemEntity cartItemEntity : storedEntity.getCartEntity().getCartItemEntityList()) {
                ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
                productEntity.setQuantity((productEntity.getQuantity()) - (cartItemEntity.getQuantity()));
                productRepository.save(productEntity);
            }
            //triggerMailForOrderPlacement(orderEntity, url);

            OrderResponseModel orderResponsemodel = new OrderResponseModel();
            BeanUtils.copyProperties(storedEntity, orderResponsemodel);

            AddressesRest addressResponseModel = new AddressesRest();
            BeanUtils.copyProperties(storedEntity.getShippingAddress(), addressResponseModel);
            orderResponsemodel.setShippingAddress(addressResponseModel);
            return orderResponsemodel;
        }

//        public List<OrderDetailsModel> findAllOrders(Long userId) {
//
//            List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId, "closed");
//
//            if (cartEntityList.isEmpty())
//                throw new RequestException(ErrorMessages.NO_ORDER_FOUND.getErrorMessages());
//
//            List<OrderDetailsModel> orderDetailsModels = new ArrayList<OrderDetailsModel>();
//            for (CartEntity cartEntity : cartEntityList) {
//                OrderEntity orderEntity = orderRepository.findByCartId(cartEntity.getCartId());
//                OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
//                BeanUtils.copyProperties(orderEntity, orderDetailsModel);
//
//                List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
//                for (CartItemEntity cartItemEntity : orderEntity.getCartEntity().getCartItemEntityList()) {
//                    CartItemDetails cartItemDetail = new CartItemDetails();
//                    cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
//                    cartItemDetail.setQuantity(cartItemEntity.getQuantity());
//                    cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
//                    cartItemDetails.add(cartItemDetail);
//                }
//                orderDetailsModel.setCartItemDetailsList(cartItemDetails);
//
//                AddressResponseModel billingAddress = new AddressResponseModel();
//                BeanUtils.copyProperties(orderEntity.getBillingAddress(), billingAddress);
//                orderDetailsModel.setBillingAddress(billingAddress);
//
//                AddressResponseModel shippingAddress = new AddressResponseModel();
//                BeanUtils.copyProperties(orderEntity.getShippingAddress(), shippingAddress);
//                orderDetailsModel.setShippingAddress(shippingAddress);
//
//                orderDetailsModels.add(orderDetailsModel);
//            }
//
//            return orderDetailsModels;
//        }

//        public OrderDetailsModel getOrderById(Long userId, Long id) {

//            Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(id);
//            if (orderEntity.isEmpty())
//                throw new RequestException(ErrorMessages.INVALID_ORDERID.getErrorMessages());
//            if (orderEntity.get().getCartEntity().getUserEntity().getUserId() != userId)
//                throw new RequestException(ErrorMessages.INVALID_USER_ORDER.getErrorMessages());
//
//            OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
//            BeanUtils.copyProperties(orderEntity.get(), orderDetailsModel);
//
//            List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
//            for (CartItemEntity cartItemEntity : orderEntity.get().getCartEntity().getCartItemEntityList()) {
//                CartItemDetails cartItemDetail = new CartItemDetails();
//                cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
//                cartItemDetail.setQuantity(cartItemEntity.getQuantity());
//                cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
//                cartItemDetails.add(cartItemDetail);
//            }
//            orderDetailsModel.setCartItemDetailsList(cartItemDetails);
//
//            AddressResponseModel billingAddress = new AddressResponseModel();
//            BeanUtils.copyProperties(orderEntity.get().getBillingAddress(), billingAddress);
//            orderDetailsModel.setBillingAddress(billingAddress);
//
//            AddressResponseModel shippingAddress = new AddressResponseModel();
//            BeanUtils.copyProperties(orderEntity.get().getShippingAddress(), shippingAddress);
//            orderDetailsModel.setShippingAddress(shippingAddress);
//
//            return orderDetailsModel;
//        }

//        public OrderResponseModel updateStatus(UserDto user, Long orderId, String status, String url) throws Exception {

//            if (!user.getUserType().equals("seller"))
//                throw new RequestException(ErrorMessages.INVALID_SELLER.getErrorMessages());
//            String status1 = status.toLowerCase();
//            if (!status1.equals("shipped") && !status1.equals("in-transit") && !status1.equals("delivered"))
//                throw new RequestException(ErrorMessages.INVALID_STATUS.getErrorMessages());
//
//            OrderResponseModel orderResponsemodel = new OrderResponseModel();
//
//            Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
//            if (orderEntity.isEmpty())
//                throw new RequestException(ErrorMessages.INVALID_ORDERID.getErrorMessages());
//            if (orderEntity.get().getOrderStatus().equals(status1))
//                throw new RequestException(ErrorMessages.SAME_STATUS.getErrorMessages());
//
//            OrderEntity orderEntity1 = orderEntity.get();
//            orderEntity1.setOrderStatus(status1);
//
//            OrderEntity orderEntity2 = orderRepository.save(orderEntity1);
//            triggerMailForOrderPlacement(orderEntity2, url);
//
//            BeanUtils.copyProperties(orderEntity2, orderResponsemodel);
//
//            AddressResponseModel addressResponseModel = new AddressResponseModel();
//            BeanUtils.copyProperties(orderEntity2.getShippingAddress(), addressResponseModel);
//            orderResponsemodel.setShippingAddress(addressResponseModel);
//            return orderResponsemodel;
//        }

//        public List<OrderDetailsModel> findOrderByStatus(Long userId, String status) {
//
//            String status1 = status.toLowerCase();
//
//            if (!status1.equals("confirmed") && !status1.equals("shipped") && !status1.equals("in-transit") && !status1.equals("delivered") && !status1.equals("cancelled"))
//                throw new RequestException(ErrorMessages.INVALID_STATUS.getErrorMessages());
//
//            List<OrderDetailsModel> orderDetailsModels = new ArrayList<OrderDetailsModel>();
//            List<CartEntity> cartEntityList = cartRepository.findByUserIdAndStatus(userId, "closed");
//
//            if (cartEntityList.isEmpty())
//                throw new RequestException(ErrorMessages.NO_ORDER_FOUND.getErrorMessages());
//
//            boolean orderfound = false;
//            for (CartEntity cartEntity : cartEntityList) {
//                OrderEntity orderEntity = orderRepository.findBycartIdandStatus(cartEntity.getCartId(), status1);
//                if (orderEntity != null) {
//                    orderfound = true;
//                    OrderDetailsModel orderDetailsModel = new OrderDetailsModel();
//                    BeanUtils.copyProperties(orderEntity, orderDetailsModel);

//                    List<CartItemDetails> cartItemDetails = new ArrayList<CartItemDetails>();
//                    for (CartItemEntity cartItemEntity : orderEntity.getCartEntity().getCartItemEntityList()) {
//                        CartItemDetails cartItemDetail = new CartItemDetails();
//                        cartItemDetail.setProductName(cartItemEntity.getProductEntity().getProductName());
//                        cartItemDetail.setQuantity(cartItemEntity.getQuantity());
//                        cartItemDetail.setTotalPrice(cartItemEntity.getTotalPrice());
//                        cartItemDetails.add(cartItemDetail);
//                    }
//                    orderDetailsModel.setCartItemDetailsList(cartItemDetails);
//
//                    AddressResponseModel billingAddress = new AddressResponseModel();
//                    BeanUtils.copyProperties(orderEntity.getBillingAddress(), billingAddress);
//                    orderDetailsModel.setBillingAddress(billingAddress);
//
//                    AddressResponseModel shippingAddress = new AddressResponseModel();
//                    BeanUtils.copyProperties(orderEntity.getShippingAddress(), shippingAddress);
//                    orderDetailsModel.setShippingAddress(shippingAddress);
//
//                    orderDetailsModels.add(orderDetailsModel);
//                }
//
//            }
//            if (!orderfound)
//                throw new RequestException(ErrorMessages.NO_ORDER_STATUS.getErrorMessages());
//            return orderDetailsModels;
//        }

//        public OrderEntity cancelOrder(Long orderId, String url, Long userId) throws Exception {
//
//            Optional<OrderEntity> orderEntity = orderRepository.findByOrderId(orderId);
//            if (orderEntity.isEmpty())
//                throw new RequestException(ErrorMessages.INVALID_ORDERID.getErrorMessages());
//            if (orderEntity.get().getCartEntity().getUserEntity().getUserId() != userId)
//                throw new RequestException(ErrorMessages.INVALID_USER_ORDER.getErrorMessages());
//            if (orderEntity.get().getOrderStatus().equals("cancelled"))
//                throw new RequestException(ErrorMessages.ALREADY_CANCELLED.getErrorMessages());
//            if (orderEntity.get().getOrderStatus().equals("delivered") || orderEntity.get().getOrderStatus().equals("in-transit"))
//                throw new RequestException(ErrorMessages.CANCEL_REJECTED.getErrorMessages());
//
//            OrderEntity orderEntity1 = orderEntity.get();
//            orderEntity1.setOrderStatus("cancelled");
//            OrderEntity cancelledOrder = orderRepository.save(orderEntity1);
//
//            for (CartItemEntity cartItemEntity : orderEntity1.getCartEntity().getCartItemEntityList()) {
//                ProductEntity productEntity = productRepository.findByProductId(cartItemEntity.getProductEntity().getProductId());
//                productEntity.setQuantity(productEntity.getQuantity() + cartItemEntity.getQuantity());
//                productRepository.save(productEntity);
//            }
//
//            triggerMailForOrderPlacement(cancelledOrder, url);
//
//            return cancelledOrder;
//
//        }

        public AddressEntity findAddressById(Long shippingAddress) {
            Optional<AddressEntity> addressEntity = addressRepository.findByAddressId(shippingAddress);

            if (addressEntity.isEmpty()) {
                throw new RuntimeException("No address with this ID");
            }

            return addressEntity.get();

        }

//        private CartEntity findCartByUserId(Long userId) {
//            Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId, "open");
//
//            if (cartEntity.isEmpty()) {
//                throw new RequestException(ErrorMessages.CART_ALREADY_CHECKED_OUT.getErrorMessages());
//            }
//            return cartEntity.get();
//        }

        public String getSiteURL(HttpServletRequest request) {
            String siteURL = request.getRequestURL().toString();

            return siteURL.replace(request.getServletPath(), "");
        }

//        public void triggerMailForOrderPlacement(OrderEntity orderEntity, String url) throws MessagingException, UnsupportedEncodingException {
//
//            emailService.sendOrderConfirmedEmail(orderEntity, url);
//
//        }

    }





}
