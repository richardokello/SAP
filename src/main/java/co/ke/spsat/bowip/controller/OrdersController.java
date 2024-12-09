package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.OrderItemRequest;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.OrderStatus;
import co.ke.spsat.bowip.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    // Create a new order
    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(
            @RequestParam Long customerId,
            @RequestParam Long userId,
            @RequestParam Long cartId) {
        Order order = orderService.createOrder(customerId, userId, cartId);
        return ResponseEntity.ok(order);
    }

    // Update the status of an order
    @PutMapping("/{orderId}/status")
    public ResponseEntity<Order> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status) {
        Order updatedOrder = orderService.updateOrderStatus(orderId, status);
        return ResponseEntity.ok(updatedOrder);
    }

    // Get all orders for a customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomer(customerId);
        return ResponseEntity.ok(orders);
    }
    @GetMapping("/checkout")
    public ResponseEntity<Order> orderChecout(@PathVariable Long cartId) throws IOException {
        Order orders = orderService.checkout(cartId);
        return ResponseEntity.ok(orders);
    }
//    @PutMapping("/update/{orderId}")
//    public Order updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus status) {
//        return orderService.updateOrderStatus(orderId, status);
//    }


    @GetMapping("/track/{orderId}")
    public Order trackOrder(@PathVariable Long orderId) {
        return orderService.trackOrder(orderId);
    }

    @GetMapping("/history/{customerId}")
    public List<Order> getOrderHistory(@PathVariable Long customerId) {
        return orderService.getOrderHistory(customerId);
    }
    // Cancel an order
    @PutMapping("/{orderId}/cancel")
    public ResponseEntity<Order> cancelOrder(@PathVariable Long orderId) {
        Order canceledOrder = orderService.cancelOrder(orderId);
        return ResponseEntity.ok(canceledOrder);
    }

    @PostMapping("/return/{orderId}/{productId}")
    public Order returnOrderItem(@PathVariable Long orderId, @PathVariable Long productId, @RequestParam int quantity) {
        return orderService.returnOrderItem(orderId, productId, quantity);
    }

    @PutMapping("/fulfill/{orderId}")
    public Order fulfillOrder(@PathVariable Long orderId) {
        return orderService.fulfillOrder(orderId);
    }

    // Add items to an order
    @PostMapping("/{orderId}/addItems")
    public ResponseEntity<Order> addItemsToOrder(
            @PathVariable Long orderId,
            @RequestBody List<OrderItemRequest> itemRequests) {
        Order updatedOrder = orderService.addItemsToOrder(orderId, itemRequests);
        return ResponseEntity.ok(updatedOrder);
    }

    // Remove items from an order
    @DeleteMapping("/{orderId}/removeItems")
    public ResponseEntity<Order> removeItemsFromOrder(
            @PathVariable Long orderId,
            @RequestBody List<Long> itemIds) {
        Order updatedOrder = orderService.removeItemsFromOrder(orderId, itemIds);
        return ResponseEntity.ok(updatedOrder);
    }

    // Update the quantity of an order item
    @PutMapping("/{orderId}/updateItemQuantity")
    public ResponseEntity<Order> updateOrderItemQuantity(
            @PathVariable Long orderId,
            @RequestParam Long itemId,
            @RequestParam Long quantity) {
        Order updatedOrder = orderService.updateOrderItemQuantity(orderId, itemId, quantity);
        return ResponseEntity.ok(updatedOrder);
    }
}
