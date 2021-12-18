package com.example.Estore.Estore.Services;

import com.example.Estore.Estore.Exception.ClientSideException;
import com.example.Estore.Estore.Services.ServiceImpl.CartEmailBuilder;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartCost;
import com.example.Estore.Estore.Shared.dto.User.UserDto;
import com.example.Estore.Estore.Ui.Model.Response.CartRequest.CartItemRest;
import com.example.Estore.Estore.Ui.Model.Response.Messages;
import com.example.Estore.Estore.io.Entity.Cart.CartItemEntity;
import com.example.Estore.Estore.io.Entity.Product.ProductEntity;
import com.example.Estore.Estore.io.Entity.User.UserEntity;
import com.example.Estore.Estore.io.Entity.WishList.WishListEntity;
import com.example.Estore.Estore.io.Repositories.Cart.CartItemRepository;
import com.example.Estore.Estore.io.Repositories.Product.ProductRepository;
import com.example.Estore.Estore.io.Repositories.User.UserRepository;
import com.example.Estore.Estore.io.Repositories.WishList.WishListRepository;
import javassist.bytecode.stackmap.BasicBlock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class CartService {
    /**
     * Inject UserRepository dependency
     */
    @Autowired
    UserRepository userRepository;
    /**
     * Inject ProductRepository dependency
     */
    @Autowired
    ProductRepository productRepository;
    /**
     * Inject CartItemRepository dependency
     */
    @Autowired
    CartItemRepository cartItemRepository;
    /**
     * Inject WishListRepository dependency
     */
    @Autowired
    WishListRepository wishListRepository;
    /**
     * Inject EmailService dependency
     */
    @Autowired
    EmailService emailService;


    /**
     * Logic for adding products to cart
     * @param userId    unique id of user
     * @param productId unique id of product
     * @param quantity  count of products that we wanted to buy
     * @return cartItemEntity
     */

    public CartItemEntity addCartItem(String userId, Long productId, Integer quantity) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        ProductEntity productEntity = productRepository.findByProductId(productId);


        if (productRepository.findByProductId(productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        if (productEntity.getQuantity() < quantity)
            throw new ClientSideException(Messages.OUT_OF_STOCK.getMessage());

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);
        if (cartItemEntity != null) {
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
            int discount = cartItemEntity.getDiscount();
            if (discount != 0) {
                cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() +
                        ((productEntity.getPrice() * (discount * 0.01)) * quantity));
                cartItemRepository.save(cartItemEntity);

                return cartItemEntity;
            }
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() + (productEntity.getPrice() * quantity));
            productEntity.setQuantity(productEntity.getQuantity() - quantity);
            productRepository.save(productEntity);
            cartItemRepository.save(cartItemEntity);
            return cartItemEntity;
        }

        CartItemEntity newcartItemEntity = new CartItemEntity();
        newcartItemEntity.setProductEntity(productEntity);
        newcartItemEntity.setProductName(productEntity.getProductName());
        newcartItemEntity.setUserEntity(userEntity);
        newcartItemEntity.setQuantity(quantity);
        newcartItemEntity.setTotalPrice(productEntity.getPrice() * quantity);
        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        cartItemRepository.save(newcartItemEntity);
        return newcartItemEntity;
    }

    /**
     * Logic for fetching all cartItems
     * @param user takes authentication of currently logged-in user
     * @return CartCost class which has list of CartItemEntity and total cost and discounts if any.
     */

    public CartCost findByUserId(UserDto user) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        if (cartItemRepository.findByUserEntity(userEntity).isEmpty())
            throw new ClientSideException(Messages.CART_IS_EMPTY.getMessage());

        List<CartItemRest> cartItemRestList = new ArrayList<>();


        double subTotal = 0;
        for (CartItemEntity cartItem : cartItemRepository.findByCartStatus(userEntity)) {
            CartItemRest cartRest = new CartItemRest();
            BeanUtils.copyProperties(cartItem,cartRest);
            cartRest.setProductId((cartItem.getProductEntity().getProductId()));
            cartItemRestList.add(cartRest);
            subTotal += cartItem.getTotalPrice();

        }
        CartCost cartCost = new CartCost();

        cartCost.setCartItem(cartItemRestList);
        cartCost.setSubTotal(subTotal);
        if (subTotal > 500) {
            cartCost.setDeliveryCharge("You are eligible for free delivery");
        }
        else {
            cartCost.setDeliveryCharge("add Rs "+ (500-subTotal)+" eligible items for free delivery");
        }
        int discount = cartItemRepository.findByCartStatus(userEntity).get(0).getDiscount();
        if (discount != 0) {
            cartCost.setDiscount(Integer.toString(discount) + "% discount applied ");
        }
        if (discount == 0) {
            cartCost.setDiscount("no discounts applied");
        }

        return cartCost;

    }

    /**
     * Logic for removing a product from cart
     * @param user      takes authentication of currently logged-in user
     * @param productId unique id of product which needs to be removed
     */

    public void removeProductFromCart(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        CartItemEntity cartItemEntity = new CartItemEntity();
        ProductEntity productEntity = productRepository.findByProductId(productId);

        if (cartItemRepository.findByUserEntityANDProductId(userEntity, productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());
        CartItemEntity cartItem = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);
        int quantity = cartItem.getQuantity();
        cartItemRepository.deleteProduct(productId, userEntity);
        productEntity.setQuantity(productEntity.getQuantity() + quantity);
        productRepository.save(productEntity);

    }

    /**
     * Logic for adding quantity of product
     * @param user      takes authentication of currently logged-in user
     * @param productId unique id of product which needs to be increased
     * @param quantity  amount that needs to be added
     * @return CartItemRest
     */

    public CartItemRest addQuantity(UserDto user, Long productId, Integer quantity) {
        ProductEntity productEntity = productRepository.findByProductId(productId);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);
        if (cartItemEntity == null)
            throw new ClientSideException(Messages.CART_NOT_ACTIVE.getMessage());
        int discount = cartItemEntity.getDiscount();
        if (discount != 0) {
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() +
                    ((productEntity.getPrice() * (discount * 0.01)) * quantity));
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
            productEntity.setQuantity(productEntity.getQuantity() - quantity);
            cartItemRepository.save(cartItemEntity);
            CartItemRest cartRest = new CartItemRest();
            BeanUtils.copyProperties(cartItemEntity,cartRest);
            cartRest.setProductId(productId);
            return cartRest;
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + quantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() + (productEntity.getPrice() * quantity));
        productEntity.setQuantity(productEntity.getQuantity() - quantity);
        productRepository.save(productEntity);
        cartItemRepository.save(cartItemEntity);
        CartItemRest cartRest = new CartItemRest();
        BeanUtils.copyProperties(cartItemEntity,cartRest);
        cartRest.setProductId(productId);
        return cartRest;

    }

    /**
     * Logic for reducing quantity of product
     * @param user      takes authentication of currently logged-in user
     * @param productId unique id of product which needs to be decreased
     * @param quantity  amount that needs to be decreased
     * @return CartItemRest
     */

    public CartItemRest reduceQuantity(UserDto user, Long productId, Integer quantity) throws ClientSideException {
        ProductEntity productEntity = productRepository.findByProductId(productId);

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);

        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);

        if (cartItemEntity == null)
            throw new ClientSideException(Messages.CART_NOT_ACTIVE.getMessage());
        int discount = cartItemEntity.getDiscount();
        if (discount != 0) {
            cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() -
                    ((productEntity.getPrice() * (discount * 0.01)) * quantity));
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() - quantity);
            productEntity.setQuantity(productEntity.getQuantity() + quantity);
            productRepository.save(productEntity);
            if (cartItemEntity.getQuantity() <= 0)

                cartItemRepository.deleteProduct(productId, userEntity);
            cartItemRepository.save(cartItemEntity);

            CartItemRest cartRest = new CartItemRest();
            BeanUtils.copyProperties(cartItemEntity,cartRest);
            cartRest.setProductId(productId);
            return cartRest;
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() - quantity);
        cartItemEntity.setTotalPrice(cartItemEntity.getTotalPrice() - (productEntity.getPrice() * quantity));
        productEntity.setQuantity(productEntity.getQuantity() + quantity);
        if (cartItemEntity.getQuantity() <= 0)
            cartItemRepository.deleteProduct(productId, userEntity);
        productRepository.save(productEntity);
        cartItemRepository.save(cartItemEntity);

        CartItemRest cartRest = new CartItemRest();
        BeanUtils.copyProperties(cartItemEntity,cartRest);
        cartRest.setProductId(productId);
        return cartRest;

    }

    /**
     * Logic for adding wishlistItems to cart
     * @param user       takes authentication of currently logged-in user
     * @param productId  unique Id of product
     * @param quantity   count of products
     * @return String
     */

    public String addWishlistToCart(UserDto user, Integer quantity, Long productId) {

        UserEntity userEntity = userRepository.findByUserId(user.getUserId());
        WishListEntity wishListEntity1 = wishListRepository.findByUserEntity(userEntity);

        List<ProductEntity> productEntityList = wishListEntity1.getProductEntityList();
        if (wishListEntity1.getProductEntityList()==null) throw new
                ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        for (ProductEntity productEntity : productEntityList) {
            if (productEntity.getProductId().equals(productId)) {
                CartItemEntity cartItemEntity = new CartItemEntity();
                cartItemEntity.setProductEntity(productEntity);
                cartItemEntity.setProductName(productEntity.getProductName());
                cartItemEntity.setUserEntity(userEntity);
                cartItemEntity.setQuantity(quantity);
                cartItemEntity.setTotalPrice(productEntity.getPrice() * quantity);
                productEntity.setQuantity(productEntity.getQuantity() - quantity);
                cartItemRepository.save(cartItemEntity);
                wishListRepository.deleteProduct(wishListEntity1.getWishListId(), productEntity.getProductId());

            }
        }

        return "WishList added successfully";
    }

    /**
     * Logic for fetching particular product from cart
     * @param user      takes authentication of currently logged-in user
     * @param productId unique id of product
     * @return CartCost
     */

    public CartItemRest findByProductId(UserDto user, Long productId) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        CartItemEntity cartItemEntity = cartItemRepository.findByUserEntityANDProductId(userEntity, productId);

        if (cartItemRepository.findByUserEntityANDProductId(userEntity, productId) == null)
            throw new ClientSideException(Messages.PRODUCT_DOES_NOT_EXIST.getMessage());

        CartItemRest cartRest = new CartItemRest();
        BeanUtils.copyProperties(cartItemEntity,cartRest);
        cartRest.setProductId(productId);
        return cartRest;

    }

    /**
     * Logic for adding discount to cartItems in case of any disputes in previous orders
     * @param user     takes authentication of currently logged-in user
     * @param discount amount that needs to be decreased from total price
     * @return String
     */

    public String applyPromoCode(UserDto user, int discount) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(user, userEntity);
        List<CartItemEntity> cartItemEntity = cartItemRepository.findByCartStatus(userEntity);

        for (CartItemEntity cartItem : cartItemEntity) {

            cartItem.setDiscount(discount);
            if (discount != 0) {

                cartItem.setTotalPrice(cartItem.getTotalPrice() * (discount * 0.01));
            }
            cartItemRepository.save(cartItem);
        }

        return "Discount applied successfully";

    }

    /**
     * Logic for sending email to user by admin in case of abandoned cart for a long time
     * (50 minutes is taken here which can be changed accordingly)
     */
    public void abandonedCartMail() {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findByStatus();
        for (CartItemEntity cartItem : cartItemEntityList) {
            long timeElapsed = (Duration.between(cartItem.getCreatedDate(), LocalDateTime.now()).get(ChronoUnit.SECONDS) / 60);
            UserEntity userEntity = cartItem.getUserEntity();
            if (timeElapsed >= 50) {

                CartEmailBuilder emailBuilder = new CartEmailBuilder();
                emailService.send(userEntity.getEmail(), emailBuilder.
                        buildAbandondedCartContent(userEntity.getFirstName(), cartItem.getProductName(),
                                cartItem.getQuantity(), cartItem.getTotalPrice()));
            }
        }
    }

}

