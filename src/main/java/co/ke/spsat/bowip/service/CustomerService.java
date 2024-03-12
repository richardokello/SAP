package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.CustomerDataRequest;
import co.ke.spsat.bowip.dtos.CustomerResponseData;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service

public class CustomerService {
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    RoutesRepository routesRepository;
    @Autowired
    CustomerCategoryRepository customerCategoryRepository;
    @Transactional
    public CustomerResponseData createCustomers(CustomerDataRequest customerRequest){

        CustomerResponseData responseData = new CustomerResponseData();
        Customers customers=new Customers();
        Regions region;
        Routes  routes=new Routes();
        Address address=new Address();
        CustomerCategory customerCategory=new CustomerCategory();
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



        List<Customers> customersList=new ArrayList<>();
        customersList.add(customers);

        Optional<Regions> existingRegion = regionRepository.findById(customerRequest.getRegions().getId());
        if (existingRegion.isPresent()) {
            region = existingRegion.get();
        }
       // if (!regionRepository.existsById(customerRequest.getRegions().getId()))
        else{
            region = new Regions();
        region.setRegionName(customerRequest.getRegions().getRegionName());
        region.setLocationName(customerRequest.getRegions().getLocationName());
        region.setLocationCOde(customerRequest.getRegions().getLocationCOde());
        region.setDescription(customerRequest.getRegions().getDescription());
        region.setCustomers(customersList);
//            List<Routes>routesList=new ArrayList<>();
//            routesList.add(routes);
//        region.setRoutes(routesList);
        regionRepository.save(region);
            responseData.setCode("00");
            responseData.setMessage("Data saved succefully");
        }



        Optional<CustomerCategory>categoryExistByID =
                customerCategoryRepository.findById(customerRequest.getCustomerCategory().getId());

        if(categoryExistByID.isPresent())
          { customerCategory=categoryExistByID.get(); }
        else {
            customerCategory.setCategoryName(customerRequest.getCustomerCategory().getCategoryName());
            customerCategory.setId(customerRequest.getCustomerCategory().getId());
            customerCategoryRepository.save(customerCategory);
        }



        Optional<Routes>routesById=routesRepository.findById(customerRequest.getRoutes().getRouteId());
        if(routesById.isPresent())
        {  routes=routesById.get();
        }
        else {

      //  if (routesRepository.existsById(customerRequest.getRoutes().getRouteId())){
            routes.setRouteId(customerRequest.getRoutes().getRouteId());

            routes.setCreatedDate(customerRequest.getRoutes().getCreatedDate());
            routes.setRegions(customerRequest.getRegions());
            routes.setDescription(customerRequest.getRoutes().getDescription());
            routes.setEndingLocation(customerRequest.getRoutes().getEndingLocation());
            routes.setDistance(customerRequest.getRoutes().getDistance());
            routes.setRouteName(customerRequest.getRoutes().getRouteName());
            routes.setEstimatedTime(customerRequest.getRoutes().getEstimatedTime());
            routes.setStartingLocation(customerRequest.getRoutes().getStartingLocation());
            routes.setCustomers(customers);
          //  routes.setCreatedDate(customerRequest.getRoutes().getCreatedDate());
            routes.setStatus("Active");
            routesRepository.save(routes);
            responseData.setCode("00");
            responseData.setMessage("Data saved succefully");
        }
        customers.setRegions(region);
        customers.setShippingAddress(address);
        customers.setRoutes(routes);
        customers.setCustomerCategory(customerCategory);
        customerRepository.save(customers);

        responseData.setCode("00");
        responseData.setMessage("Data saved succefully");


       return responseData;
    }
public List<Customers>getCustomersPerRoute(Long routeId){
   return  customerRepository.findCustomersByRouteId(routeId);

}
//    public List<Customers> getCustomersByRouteId(Long routeId) {
//        Optional<Routes> routeOptional = routesRepository.findById(routeId);
//
//        if (routeOptional.isPresent()) {
//            Routes route = routeOptional.get();
//            return route.getCustomers();
//        } else {
//            // Handle the case when the route with the given ID is not found
//            return Collections.emptyList();
//        }
//    }
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
