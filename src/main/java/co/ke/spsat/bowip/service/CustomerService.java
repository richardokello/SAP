package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.CustomerDataRequest;
import co.ke.spsat.bowip.dtos.CustomerResponseData;
import co.ke.spsat.bowip.entities.Address;
import co.ke.spsat.bowip.entities.Customers;
import co.ke.spsat.bowip.entities.Regions;
import co.ke.spsat.bowip.repositories.AddressRepository;
import co.ke.spsat.bowip.repositories.CustomerRepository;
import co.ke.spsat.bowip.repositories.RegionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;

    public CustomerResponseData createCustomers(CustomerDataRequest customerRequest){

        CustomerResponseData responseData = new CustomerResponseData();

        Customers customers=new Customers();
        customers.setFirstName(customerRequest.getFirstName());
        customers.setLastName(customerRequest.getLastName());
        customers.setEmail(customerRequest.getEmail());
        customers.setRegions(customerRequest.getRegions());
        customers.setShippingAddress(customerRequest.getAddress());
        customers.setPhone(customerRequest.getPhone());
        customers.setKRAPIN(customerRequest.getKRAPIN());
        customers.setBusinessName(customers.getBusinessName());
        customers.setBusinessLicenseNumber(customerRequest.getBusinessLicenseNumber());
        customers.setBusinessPrimaryContactNo(customerRequest.getBusinessPrimaryContactNo());
        customers.setBusinessSecondaryContactNo(customers.getBusinessSecondaryContactNo());
        customers.setLocalRegistrationNumber(customers.getLocalRegistrationNumber());
                Address address=new Address();
        address.setAddressLine1(customerRequest.getAddress().getAddressLine1());
        address.setAddressLine2(customerRequest.getAddress().getAddressLine2());
        address.setPostalCode(customerRequest.getAddress().getPostalCode());
        address.setCountry(customerRequest.getAddress().getCountry());
        address.setCity(customerRequest.getAddress().getCity());
        address.setState(customerRequest.getAddress().getState());
        address.setStreet(customerRequest.getAddress().getStreet());

        addressRepository.save(address);
//        Regions region = regionRepository.findById(customerRequest.getRegions().getId())
//                .orElseThrow(() -> new EntityNotFoundException("Region not found"));
        Regions region=new Regions();
        if (!regionRepository.existsById(customerRequest.getRegions().getId())){
        region.setRegionName(customerRequest.getRegions().getRegionName());
        region.setLocationName(customerRequest.getRegions().getLocationName());
        region.setLocationCOde(customerRequest.getRegions().getLocationCOde());
        region.setDescription(customerRequest.getRegions().getDescription());
        regionRepository.save(region);}
        customers.setRegions(region);
        customers.setShippingAddress(address);
        customerRepository.save(customers);
      responseData.setCode("00");
      responseData.setMessage("Data saved succefully");
       return responseData;
    }

    public List<CustomerDataRequest>  getAllCustomerData(Integer pageNo, Integer pageSize, String sortBy){

            List<CustomerDataRequest> responseDataList = new ArrayList<>();

            List<Customers> customersList = customerRepository.findAll();

            for (Customers customer : customersList) {
                CustomerDataRequest responseData = new CustomerDataRequest();
                responseData.setCustomerId(customer.getCustomerId());
                responseData.setFirstName(customer.getFirstName());
                responseData.setLastName(customer.getLastName());
                responseData.setEmail(customer.getEmail());
                responseData.setPhone(customer.getPhone());

                // Set shipping address
                Address shippingAddress = customer.getShippingAddress();
                responseData.setShippingAddress(shippingAddress);

                // Set business details
                responseData.setBusinessName(customer.getBusinessName());
                responseData.setLocalRegistrationNumber(customer.getLocalRegistrationNumber());
                responseData.setBusinessLicenseNumber(customer.getBusinessLicenseNumber());
                responseData.setKRAPIN(customer.getKRAPIN());
                responseData.setBusinessPrimaryContactNo(customer.getBusinessPrimaryContactNo());
                responseData.setBusinessSecondaryContactNo(customer.getBusinessSecondaryContactNo());

                // Set regions
                Regions regions = customer.getRegions();
                responseData.setRegions(regions);

                responseDataList.add(responseData);
            }

            return responseDataList;
        }



}
