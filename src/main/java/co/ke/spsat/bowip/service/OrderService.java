package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.dtos.OrderItemRequest;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.payment.mpesa.MpesaIntegration;
import co.ke.spsat.bowip.repositories.*;
import co.ke.spsat.bowip.user.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductsRepository productRepository;
    private final UsersRepository usersRepository;
    private final ShoppingCartService shoppingCartService;
    private final ShoppingCartRepository shoppingCartRepository;
    private final StockRepository stockRepository;
    private final MpesaIntegration mpesaIntegration;
    private final CartItemRepository cartItemRepository;
    private  final BatchRepository batchRepository;
    private final DiscountRepository discountRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private COGSService cogsService;

    public OrderService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, UsersRepository usersRepository, CustomerRepository customerRepository, ProductsRepository productRepository, ShoppingCartService shoppingCartService, StockRepository stockRepository, MpesaIntegration mpesaIntegration, CartItemRepository cartItemRepository, BatchRepository batchRepository, DiscountRepository discountRepository) {
        this.orderRepository = orderRepository;
        this.usersRepository=usersRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.stockRepository = stockRepository;
        this.mpesaIntegration = mpesaIntegration;
        this.cartItemRepository = cartItemRepository;
        this.batchRepository = batchRepository;
        this.discountRepository = discountRepository;
    }



//    public Order createOrder(Long customerId, Long userId, Long cartId) {
//        Customers customer = customerRepository.findById(customerId)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
//        Optional<Users> users = usersRepository.findByUserIdAndActivatedAndRolesIs(userId, AppConstants.ROLE_FIELD_USERS );
//
//        Order order = new Order();
//        order.setCustomer(customer);
//        order.setCreatedBy(users.get().getEmail());
//        order.setOrderDate(LocalDateTime.now());
//        order.setOrderStatus(OrderStatus.NEW);
//
//        List<OrderItemRequest> itemRequests = shoppingCartService.getCartItems(cartId).stream()
//                .map(cartItem -> new OrderItemRequest(cartItem.getProducts().getProductId(),  cartItem.getQuantity()))
//                .collect(Collectors.toList());
//
//        order.setOrderItems(createOrderItems(itemRequests));
//        order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
//        Order savedOrder= orderRepository.save(order);
//        notificationService.sendOrderConfirmation(order.getCustomer().getBusinessEmail(), savedOrder);
//     return savedOrder;
//    }
//TODO: TO WRITE LOGIC THAT HANDLES SUBSTRACTION OF PRODUCTS
    public Order createOrder(Long customerId, Long userId, Long cartId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Optional<Users> users = usersRepository.findByUserIdAndActivatedAndRolesIs(userId, AppConstants.ROLE_FIELD_USERS);

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedBy(users.get().getEmail());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.NEW);

        List<OrderItemRequest> itemRequests = shoppingCartService.getCartItems(cartId).stream()
                .map(cartItem -> new OrderItemRequest(cartItem.getProducts().getProductId(), cartItem.getQuantity()))
                .collect(Collectors.toList());

        List<OrderItem> orderItems = createOrderItems(itemRequests);
        order.setOrderItems(orderItems);

        // Calculate total COGS for the order using FIFO
        double totalCOGS = 0.0;
        for (OrderItem item : orderItems) {
            double productCOGS = cogsService.calculateCOGS(item.getProduct().getProductId(), item.getQuantity());
            item.setCostOfGoodsSold(productCOGS);  // Set COGS for each order item
            totalCOGS += productCOGS;  // Sum COGS for the order
        }

        order.setTotalCOGS(totalCOGS);  // Set total COGS for the order
        order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));  // Calculate total amount

        Order savedOrder = orderRepository.save(order);
        notificationService.sendOrderConfirmation(order.getCustomer().getBusinessEmail(), savedOrder);
        return savedOrder;
    }


    public Order updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(status);
        return orderRepository.save(order);
    }

    // Get orders by customer

    public List<Order> getOrdersByCustomer(Long customerId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        return orderRepository.findByCustomer(customer);
    }

    private List<OrderItem> createOrderItems(List<OrderItemRequest> itemRequests) {
        return itemRequests.stream()
                .map(request -> {
                    Products product = productRepository.findById(request.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
                    if (!isProductAvailable(product, request.getQuantity())) {
                        throw new IllegalArgumentException("Product not available in the requested quantity");
                    }
                    if (request.getBatchNo()!=null){
                       Batch batches= batchRepository.findByBatchNo(request.getBatchNo()).orElseThrow(() -> new ResourceNotFoundException("Batch with that Id not found"));
                       if(batches.getQuantity()>request.getQuantity()&&batches.getExpirationDate().isBefore(LocalDate.now())){
                           batches.setQuantity(batches.getQuantity()- request.getQuantity());
                           batchRepository.save(batches);
                       }
                    }
                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(request.getQuantity());
                    item.setTotalPrice(product.getSellingPrice().multiply(BigDecimal.valueOf(request.getQuantity())));
                    return item;
                })
                .collect(Collectors.toList());
    }
private Discount applyDiscountToAnOrder(String discountCode, Long orderId)
{
 Discount discount= discountRepository.findByCode(discountCode).orElseThrow(()-> new ResourceNotFoundException("Discount code not found"));
 Order order= orderRepository.findById(orderId).orElseThrow(()-> new ResourceNotFoundException("Order not found"));
 BigDecimal discountedAmount= (order.getTotalOrderAmount().subtract (order.getTotalOrderAmount().multiply(discount.getDiscountPercentage())).divide(new BigDecimal(100), RoundingMode.HALF_UP));
 order.setTotalOrderAmount(discountedAmount);
 discountRepository.save(discount);
 return discount;
}

    private boolean isProductAvailable(Products product, Long quantity) {
        Stock stock=stockRepository.findById(product.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Stock not found"));
         return stock.getQuantityOnHand()>= quantity;
      ///  return product.getQuantityInStock() >= quantity;
    }

    private BigDecimal calculateTotalAmount(List<OrderItem> items) {
        return  items.stream()
                .map(item -> item.getUnitPrice()
                .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

//    private BigDecimal calculateOrderTotalAmount(List<OrderItem> orderItems) {
//        return orderItems.stream()
//                .map(OrderItem::getItemPriceAmount)
//                .reduce(BigDecimal.ZERO, BigDecimal::add);
//    }

    // Additional Methods

    public Order cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    public Order trackOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public List<Order> getOrderHistory(Long customerId) {
        return orderRepository.findByCustomerCustomerId(customerId);
    }

    public Order addItemsToOrder(Long orderId, List<OrderItemRequest> itemRequests) {
      //  Batch batch = batchRepository.findById(batchId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        List<OrderItem> items = createOrderItems(itemRequests);
       // order.getOrderItems().addAll(items);
        order.setOrderItems(items);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));
        return order;//orderRepository.save(order);
    }
//Method that handles payment of orders.

    public Order checkout(Long cartId) throws IOException {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        Order order = new Order();
        order.setShoppingCart(cart);
        order.setOrderDate(LocalDateTime.now());
        BigDecimal taxAmount = calculateTax(cart);
        order.setTaxAmount(taxAmount);
        order.setOrderStatus(OrderStatus.PENDING);
     //   order.setProducts(cart.getCartItems().);
        order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));
        try {
            mpesaIntegration.processMpesaSTKPush(order.getCustomer().getBusinessPrimaryContactNo(), order.getTotalOrderAmount() );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            order.setOrderStatus(OrderStatus.COMPLETED);
            clearCart(cartId);
            // Additional checkout logic (e.g., payment processing, stock deduction) can be added here

            return orderRepository.save(order);

    }
    public void clearCart(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElseThrow(() -> new RuntimeException("Cart not found"));
        cartItemRepository.deleteAll(cart.getCartItems());
    }

    private BigDecimal calculateTax(ShoppingCart cart) {
        // Example tax calculation logic
       BigDecimal taxRate = new BigDecimal("0.16"); // 10% tax rate
        return cart.getTotalAmount().multiply(taxRate);
    }
    public Order removeItemsFromOrder(Long orderId, List<Long> itemIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.getOrderItems().removeIf(item -> itemIds.contains(item.getItemId()));
        order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));
        return orderRepository.save(order);
    }

    public Order updateOrderItemQuantity(Long orderId, Long itemId, Long quantity) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.getOrderItems().forEach(item -> {
            if (item.getItemId().equals(itemId)) {
                if (!isProductAvailable(item.getProduct(), quantity)) {
                    throw new IllegalArgumentException("Product not available in the requested quantity");
                }
                item.setQuantity(quantity);
                item.setTotalPrice(item.getProduct().getPrice().multiply(BigDecimal.valueOf(quantity)) );
            }
        });
        order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));
        return orderRepository.save(order);
    }
    public Order fulfillOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.setOrderStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }
    public Order returnOrderItem(Long orderId, Long productId, int quantity) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        Optional<OrderItem> orderItemOptional = order.getOrderItems().stream()
                .filter(item -> item.getProduct().getProductId().equals(productId))
                .findFirst();

        if (orderItemOptional.isPresent()) {
            OrderItem orderItem = orderItemOptional.get();
            if (orderItem.getQuantity() >= quantity) {
                orderItem.setQuantity(orderItem.getQuantity() - quantity);
                orderItem.setTotalPrice(orderItem.getUnitPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
                if (orderItem.getQuantity() == 0) {
                    order.getOrderItems().remove(orderItem);
                }
                order.setProducts(orderItem.getOrder().getProducts());
                order.setTotalOrderAmount(calculateTotalAmount(order.getOrderItems()));
                return orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("Quantity to return exceeds quantity in order");
            }
        } else {
            throw new ResourceNotFoundException("Order item not found");
        }
    }
}
