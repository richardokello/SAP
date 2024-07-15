package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.ShippingOption;
import co.ke.spsat.bowip.entities.ShippingOptionRepository;
import co.ke.spsat.bowip.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShippingService {
    @Autowired
    private ShippingOptionRepository shippingOptionRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationService notificationService;

    public List<ShippingOption> getAvailableShippingOptions() {
        return shippingOptionRepository.findAll();
    }

    public Order setShippingOption(Long orderId, Long shippingOptionId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        ShippingOption shippingOption = shippingOptionRepository.findById(shippingOptionId)
                .orElseThrow(() -> new RuntimeException("Shipping option not found"));

        order.setShippingOption(shippingOption);
        Order updatedOrder = orderRepository.save(order);
     //   return orderRepository.save(order);
        // Send notification
        notificationService.sendShippingUpdate(order.getCustomer().getBusinessEmail(), updatedOrder);

        return updatedOrder;
    }
}
