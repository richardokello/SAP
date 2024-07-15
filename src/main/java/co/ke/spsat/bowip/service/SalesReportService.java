package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.OrderStatus;
import co.ke.spsat.bowip.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class SalesReportService {
    @Autowired
    private OrderRepository orderRepository;


    public List<Order> getSalesReport() {
        // Add logic to filter orders for a specific period, status, etc.
        return orderRepository.findAll();
    }

    public Double getTotalSales() {
        return orderRepository.findAll().stream()
                .mapToDouble(Order::getOrderAmount)
                .sum();
    }

    public Double getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .mapToDouble(Order::getOrderAmount)
                .sum();
    }

    public List<Order> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetweenAndOrderStatus(startDate, endDate, OrderStatus.COMPLETED);
    }
    public Double getTotalSales(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetweenAndOrderStatus(startDate, endDate, OrderStatus.COMPLETED).stream()
                .mapToDouble(Order::getOrderAmount)
                .sum();
    }
}
