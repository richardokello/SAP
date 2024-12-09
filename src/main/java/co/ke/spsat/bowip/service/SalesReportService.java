package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.entities.OrderStatus;
import co.ke.spsat.bowip.entities.SalesReports;
import co.ke.spsat.bowip.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class SalesReportService {
    @Autowired
    private OrderRepository orderRepository;


//    public List<Order> getSalesReport() {
//        // Add logic to filter orders for a specific period, status, etc.
//        return orderRepository.findAll();
//    }

    public BigDecimal getTotalSales() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalOrderAmount)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

    }

    public List<SalesReports> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orders = orderRepository.findByOrderDateBetweenAndOrderStatus(startDate, endDate,OrderStatus.COMPLETED);
        List<SalesReports> report = new ArrayList<>();

        for (Order order : orders) {
            SalesReports dto = new SalesReports();
            dto.setOrderId(order.getOrderId());
            dto.setOrderDate(order.getOrderDate());
            dto.setTotalAmount(order.getTotalOrderAmount());
            dto.setCustomerName(order.getCustomer().getBusinessName());
            report.add(dto);
        }
        return report;
    }


    public BigDecimal getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.findByOrderDateBetween(startDate, endDate).stream()
                .map(Order::getTotalOrderAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }
//
//    public List<Order> getSalesReport(LocalDateTime startDate, LocalDateTime endDate) {
//        return orderRepository.findByOrderDateBetweenAndOrderStatus(startDate, endDate, OrderStatus.COMPLETED);
//    }lo
public BigDecimal getTotalSales(LocalDateTime startDate,LocalDateTime endDate) {
    List<Order> orders = orderRepository.findByOrderDateBetweenAndOrderStatus(startDate, endDate, OrderStatus.COMPLETED);
    return orders.stream()
            .map(Order::getTotalOrderAmount)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
}

}
