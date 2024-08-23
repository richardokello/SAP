package co.ke.spsat.bowip.service;

import co.ke.spsat.bowip.dtos.CustomerDataRequest;
import co.ke.spsat.bowip.dtos.CustomerResponseData;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.repositories.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomerService {
    @Autowired
    RegionRepository regionRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    AddressRepository addressRepository;
    @Autowired
    private CustomerInteractionRepository interactionRepository;

    @Autowired
    private CustomerFeedbackRepository feedbackRepository;

    @Autowired
    private CustomerSupportTicketRepository supportTicketRepository;
    @Autowired
    RoutesRepository routesRepository;

    List<Customers> customers = new ArrayList<>();
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
//public List<Customers>getCustomersPerRoute(Long routeId){
//
//   //return  customerRepository.findCustomersByRouteId(routeId);
//
//}
    public  List<Customers> getCustomers(Integer pageNo, Integer pageSize, String sortBy) {
        return customerRepository.findAll();

    }
    public List<Customers> getCustomersByRegion(Regions region) {
     return  customerRepository.findCustomersByRegionsId(region.getId());
    }
     public Optional<Customers> getCustomersByCustomerCode(String customerCode) {
     return customerRepository.getCustomersByCustomerCode(customerCode);
     }
//  public List<Customers> getCustomersByRoute(Routes route) {
//        return customerRepository.findCustomersByRouteId(route.getRouteId());
// }


    public List<CustomerDataRequest> getCustomersByRouteId(Long routeId,Integer pageNo, Integer pageSize, String sortBy) {
        Optional<Routes> routeOptional = routesRepository.findById(routeId);

        if (routeOptional.isPresent()) {
            Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
            List<Customers> customersList=customerRepository.findCustomersByRouteId(routeId, paging);
            return customersList.stream()
                    .map(this::mapToCustomerRequestData)
                    .collect(Collectors.toList());
        } else {
            log.error("the route id {} does not exist");
            // Handle the case when the route with the given ID is not found
            return null;
        }
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
                responseData.setRoutes(customer.getRoutes());
                responseData.setCustomerCategory(customer.getCustomerCategory());
            // Set regions
                //Regions regions = customer.getRegions();
                responseData.setRegions(customer.getRegions());

                responseDataList.add(responseData);
            }

            return responseDataList;
        }


    public CustomerInteraction createInteraction(CustomerInteraction interaction) {
        return interactionRepository.save(interaction);
    }

    public List<CustomerInteraction> getAllInteractions() {
        return interactionRepository.findAll();
    }

    // Customer Feedback and Reviews
    public CustomerFeedback createFeedback(CustomerFeedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<CustomerFeedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    // Customer Support Tickets
    public CustomerSupportTicket createSupportTicket(CustomerSupportTicket supportTicket) {
        supportTicket.setCreatedDate(new Date());
        supportTicket.setStatus("Open");
        return supportTicketRepository.save(supportTicket);
    }

    public List<CustomerSupportTicket> getAllSupportTickets() {
        return supportTicketRepository.findAll();
    }

    public CustomerSupportTicket updateSupportTicket(Long id, CustomerSupportTicket supportTicketDetails) {
        CustomerSupportTicket supportTicket = supportTicketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Support ticket not found"));
        supportTicket.setIssueDescription(supportTicketDetails.getIssueDescription());
        supportTicket.setStatus(supportTicketDetails.getStatus());
        supportTicket.setResolvedDate(supportTicketDetails.getResolvedDate());
        return supportTicketRepository.save(supportTicket);
    }

private CustomerDataRequest mapToCustomerRequestData(Customers customers){
    return CustomerDataRequest.builder()
            .customerId(customers.getCustomerId())
            .customerCode(customers.getCustomerCode())
            .customerCategory(customers.getCustomerCategory())
            .businessEmail(customers.getBusinessEmail())
            .businessName(customers.getBusinessName())
            .businessLicenseNumber(customers.getBusinessLicenseNumber())
            .localRegistrationNumber(customers.getLocalRegistrationNumber())
            .KRAPIN(customers.getKRA_PIN())
            .businessPrimaryContactNo(customers.getBusinessPrimaryContactNo())
            .directorName(customers.getDirectorName())
             .regions(customers.getRegions())
            .routes(customers.getRoutes())
            .shippingAddress(customers.getShippingAddress())
            .build();

}

}
