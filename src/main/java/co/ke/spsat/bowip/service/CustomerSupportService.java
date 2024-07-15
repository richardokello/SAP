package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.Exception.ResourceNotFoundException;
import co.ke.spsat.bowip.entities.CustomerSupport;
import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.repositories.CustomerSupportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerSupportService {
    @Autowired
    private CustomerSupportRepository customerSupportRepository;

    public CustomerSupport submitQuery(Long customerId, String query) {
        CustomerSupport support = new CustomerSupport();
        support.setCustomer(new Customers());
        support.getCustomer().setCustomerId(customerId);
        support.setQuery(query);
        support.setStatus("Open");
        return customerSupportRepository.save(support);
    }

    public List<CustomerSupport> getCustomerSupportQueries(Long customerId) {
        return customerSupportRepository.findByCustomerCustomerId(customerId);
    }

    public CustomerSupport respondToQuery(Long supportId, String response) {
        CustomerSupport support = customerSupportRepository.findById(supportId)
                .orElseThrow(() -> new ResourceNotFoundException("Support query not found"));
        support.setResponse(response);
        support.setStatus("Closed");
        return customerSupportRepository.save(support);
    }
}
