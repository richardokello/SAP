package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.dtos.OrderItemRequest;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
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

    @Autowired
    private NotificationService notificationService;

    public OrderService(OrderRepository orderRepository, ShoppingCartRepository shoppingCartRepository, UsersRepository usersRepository, CustomerRepository customerRepository, ProductsRepository productRepository, ShoppingCartService shoppingCartService, StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.usersRepository=usersRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.shoppingCartService = shoppingCartService;
        this.shoppingCartRepository = shoppingCartRepository;
        this.stockRepository = stockRepository;
    }



    public Order createOrder(Long customerId, Long userId, Long cartId) {
        Customers customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        Optional<Users> users = usersRepository.findByUserIdAndActivatedAndRolesIs(userId, AppConstants.ROLE_FIELD_USERS );

        Order order = new Order();
        order.setCustomer(customer);
        order.setCreatedBy(users.get().getEmail());
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.NEW);

        List<OrderItemRequest> itemRequests = shoppingCartService.getCartItems(cartId).stream()
                .map(cartItem -> new OrderItemRequest(cartItem.getProducts().getProductId(),  cartItem.getQuantity()))
                .collect(Collectors.toList());

        order.setOrderItems(createOrderItems(itemRequests));
        order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
        Order savedOrder= orderRepository.save(order);
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

                    OrderItem item = new OrderItem();
                    item.setProduct(product);
                    item.setQuantity(request.getQuantity());
                    item.setTotalPrice(product.getSellingPrice()*(request.getQuantity()) );
                    return item;
                })
                .collect(Collectors.toList());
    }


    private boolean isProductAvailable(Products product, Long quantity) {
        Stock stock=stockRepository.findById(product.getProductId())
                .orElseThrow(()-> new ResourceNotFoundException("Stock not found"));
         return stock.getQuantityOnHand()>= quantity;
      ///  return product.getQuantityInStock() >= quantity;
    }

    private double calculateTotalAmount(List<OrderItem> items) {
        return items.stream().mapToDouble(item -> item.getTotalPrice() * item.getQuantity()).sum();
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
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        List<OrderItem> items = createOrderItems(itemRequests);
      //  order.getOrderItems().addAll(items);
        order.setOrderItems(items);
        order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
        return orderRepository.save(order);
    }


    public Order checkout(Long cartId) {
        ShoppingCart cart = shoppingCartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Order order = new Order();
        order.setShoppingCart(cart);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderStatus(OrderStatus.PENDING);

        Double taxAmount = calculateTax(cart);
        order.setTaxAmount(taxAmount);

        // Additional checkout logic (e.g., payment processing, stock deduction) can be added here

        return orderRepository.save(order);
    }

    private Double calculateTax(ShoppingCart cart) {
        // Example tax calculation logic
        Double taxRate = Double.valueOf("0.16"); // 10% tax rate
        return cart.getTotalAmount()*(taxRate);
    }
    public Order removeItemsFromOrder(Long orderId, List<Long> itemIds) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        order.getOrderItems().removeIf(item -> itemIds.contains(item.getItemId()));
        order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
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
                item.setTotalPrice(item.getProduct().getPrice() * quantity);
            }
        });
        order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
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
                orderItem.setTotalPrice(orderItem.getUnitPrice()*(orderItem.getQuantity()));
                if (orderItem.getQuantity() == 0) {
                    order.getOrderItems().remove(orderItem);
                }
                order.setOrderAmount(calculateTotalAmount(order.getOrderItems()));
                return orderRepository.save(order);
            } else {
                throw new IllegalArgumentException("Quantity to return exceeds quantity in order");
            }
        } else {
            throw new ResourceNotFoundException("Order item not found");
        }
    }
}
