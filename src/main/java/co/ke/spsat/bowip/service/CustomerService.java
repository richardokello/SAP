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

        customers.setRegions(customerRequest.getRegions());
        customers.setCustomerCode(customerRequest.getCustomerCode());
        customers.setRegions(customerRequest.getRegions());
        customers.setDirectorName(customerRequest.getDirectorName());
        customers.setBusinessEmail(customerRequest.getBusinessEmail());
        customers.setShippingAddress(customerRequest.getShippingAddress());
        customers.setKRA_PIN(customerRequest.getKRAPIN());
        customers.setBusinessName(customerRequest.getBusinessName());
        customers.setBusinessLicenseNumber(customerRequest.getBusinessLicenseNumber());
        customers.setBusinessPrimaryContactNo(customerRequest.getBusinessPrimaryContactNo());
        customers.setLocalRegistrationNumber(customerRequest.getLocalRegistrationNumber());
                Address address=new Address();
        address.setAddressLine1(customerRequest.getShippingAddress().getAddressLine1());
        address.setAddressLine2(customerRequest.getShippingAddress().getAddressLine2());
        address.setPostalCode(customerRequest.getShippingAddress().getPostalCode());
        address.setCountry(customerRequest.getShippingAddress().getCountry());
        address.setCity(customerRequest.getShippingAddress().getCity());
        address.setState(customerRequest.getShippingAddress().getState());
        address.setStreet(customerRequest.getShippingAddress().getStreet());

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
                responseData.setCustomerCode(customer.getCustomerCode());
                responseData.setDirectorName(customer.getDirectorName());
                Address shippingAddress = customer.getShippingAddress();
                responseData.setShippingAddress(shippingAddress);
                responseData.setBusinessName(customer.getBusinessName());
                responseData.setLocalRegistrationNumber(customer.getLocalRegistrationNumber());
                responseData.setBusinessLicenseNumber(customer.getBusinessLicenseNumber());
                responseData.setKRAPIN(customer.getKRA_PIN());
                responseData.setBusinessPrimaryContactNo(customer.getBusinessPrimaryContactNo());
                responseData.setBusinessEmail(customer.getBusinessEmail());
            // Set regions
                Regions regions = customer.getRegions();
                responseData.setRegions(regions);

                responseDataList.add(responseData);
            }

            return responseDataList;
        }



}
