package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.repositories.OrderRepository;
import co.ke.spsat.bowip.repositories.TrafficRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ConversionRateService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private TrafficRepository trafficRepository;

    public double getConversionRate() {
        long totalVisits = trafficRepository.count();
        long totalOrders = orderRepository.count();
        return (double) totalOrders / totalVisits * 100;
    }

    public double getConversionRateByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Long totalVisits = trafficRepository.countByVisitDateBetween(startDate, endDate);
        Long totalOrders = orderRepository.countByOrderDateBetween(startDate, endDate);
        return (double) totalOrders / totalVisits * 100;
    }
}
