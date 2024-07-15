package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Order;
import co.ke.spsat.bowip.repositories.CustomerRepository;
import co.ke.spsat.bowip.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                        .mapToDouble(Order::getOrderAmount)
                        .sum() > 100000)
                .collect(Collectors.toList());
    }
}
