package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.repositories.CustomerRepository;
import co.ke.spsat.bowip.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerBehaviorService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderRepository orderRepository;

    public List<Customers> getFrequentCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> orderRepository.countByCustomer(customer) > 5)
                .collect(Collectors.toList());
    }

    public List<Customers> getHighSpendingCustomers() {
        return customerRepository.findAll().stream()
                .filter(customer -> orderRepository.findByCustomer(customer).stream()
                        .map(Order::getTotalOrderAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .compareTo(new BigDecimal(100000))>0)
                .collect(Collectors.toList());
    }
}
