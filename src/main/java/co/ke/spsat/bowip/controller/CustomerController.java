package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.CustomerDataRequest;
import co.ke.spsat.bowip.dtos.CustomerResponseData;
import co.ke.spsat.bowip.dtos.ProductResponse;
import co.ke.spsat.bowip.entities.*;
import co.ke.spsat.bowip.service.CustomerService;
import co.ke.spsat.bowip.service.CustomerSupportService;
import co.ke.spsat.bowip.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @Autowired
    private WishlistService wishlistService;
    @Autowired
    private CustomerSupportService customerSupportService;
    @PostMapping("/createCustomer")
    public ResponseEntity<CustomerResponseData>createCustomers(@RequestBody @Validated CustomerDataRequest customerDataRequest){
        CustomerResponseData customerResponseData=new CustomerResponseData();

        try{

            customerResponseData=  customerService.createCustomers(customerDataRequest);
            return new ResponseEntity<>(customerResponseData, HttpStatus.OK);

        } catch (Exception e){
            customerResponseData.setCode("501");
            customerResponseData.setMessage(e.getMessage());
            return new ResponseEntity<CustomerResponseData> (customerResponseData, HttpStatus.CONFLICT);
        }
    }
    @GetMapping("/customerDataList")
    public ResponseEntity<List<CustomerDataRequest>>getListOfCustomer(@RequestParam(defaultValue = "0") Integer pageNo,
                                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                                           @RequestParam(defaultValue = "id") String sortBy){
        try{

            List<CustomerDataRequest> productList;
            productList=customerService.getAllCustomerData(pageNo,pageSize,sortBy);
            if(!productList.isEmpty()){
                return new ResponseEntity<>(productList,new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new HttpHeaders(),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @GetMapping("/getCustomerPerRoute/{id}")
    public ResponseEntity<List<CustomerDataRequest>>getCustomerPerRoute(@PathVariable Long id,@RequestParam(defaultValue = "0") Integer pageNo,
                                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                                              @RequestParam(defaultValue = "id") String sortBy){
        try{

            List<CustomerDataRequest> customers;
            customers=customerService.getCustomersByRouteId(id,  pageNo,
                     pageSize,
                    sortBy);
            if(!customers.isEmpty()){
                return new ResponseEntity<>(customers,new HttpHeaders(), HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(new HttpHeaders(),HttpStatus.NO_CONTENT);
            }
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/{customerId}/wishlists")
    public Wishlist createWishlist(@PathVariable Long customerId) {
        return wishlistService.createWishlist(customerId);
    }

    @PutMapping("/wishlists/{wishlistId}/add/{productId}")
    public Wishlist addProductToWishlist(@PathVariable Long wishlistId, @PathVariable Long productId) {
        return wishlistService.addProductToWishlist(wishlistId, productId);
    }

    @PutMapping("/wishlists/{wishlistId}/remove/{productId}")
    public Wishlist removeProductFromWishlist(@PathVariable Long wishlistId, @PathVariable Long productId) {
        return wishlistService.removeProductFromWishlist(wishlistId, productId);
    }

    @GetMapping("/{customerId}/wishlists")
    public List<Wishlist> getWishlists(@PathVariable Long customerId) {
        return wishlistService.getWishlists(customerId);
    }

    @PostMapping("/{customerId}/support")
    public CustomerSupport submitQuery(@PathVariable Long customerId, @RequestParam String query) {
        return customerSupportService.submitQuery(customerId, query);
    }

    @GetMapping("/{customerId}/support")
    public List<CustomerSupport> getCustomerSupportQueries(@PathVariable Long customerId) {
        return customerSupportService.getCustomerSupportQueries(customerId);
    }

    @PutMapping("/support/{supportId}/respond")
    public CustomerSupport respondToQuery(@PathVariable Long supportId, @RequestParam String response) {
        return customerSupportService.respondToQuery(supportId, response);
    }

    // Customer Interaction History
    @PostMapping("/interactions")
    public ResponseEntity<CustomerInteraction> createInteraction(@RequestBody CustomerInteraction interaction) {
        CustomerInteraction createdInteraction = customerService.createInteraction(interaction);
        return ResponseEntity.ok(createdInteraction);
    }

    @GetMapping("/interactions")
    public ResponseEntity<List<CustomerInteraction>> getAllInteractions() {
        List<CustomerInteraction> interactions = customerService.getAllInteractions();
        return ResponseEntity.ok(interactions);
    }

    // Customer Feedback and Reviews
    @PostMapping("/feedbacks")
    public ResponseEntity<CustomerFeedback> createFeedback(@RequestBody CustomerFeedback feedback) {
        CustomerFeedback createdFeedback = customerService.createFeedback(feedback);
        return ResponseEntity.ok(createdFeedback);
    }

    @GetMapping("/feedbacks")
    public ResponseEntity<List<CustomerFeedback>> getAllFeedbacks() {
        List<CustomerFeedback> feedbacks = customerService.getAllFeedbacks();
        return ResponseEntity.ok(feedbacks);
    }

    // Customer Support Tickets
    @PostMapping("/support-tickets")
    public ResponseEntity<CustomerSupportTicket> createSupportTicket(@RequestBody CustomerSupportTicket supportTicket) {
        CustomerSupportTicket createdSupportTicket = customerService.createSupportTicket(supportTicket);
        return ResponseEntity.ok(createdSupportTicket);
    }

    @GetMapping("/support-tickets")
    public ResponseEntity<List<CustomerSupportTicket>> getAllSupportTickets() {
        List<CustomerSupportTicket> supportTickets = customerService.getAllSupportTickets();
        return ResponseEntity.ok(supportTickets);
    }

    @PutMapping("/support-tickets/{id}")
    public ResponseEntity<CustomerSupportTicket> updateSupportTicket(@PathVariable Long id, @RequestBody CustomerSupportTicket supportTicketDetails) {
        CustomerSupportTicket updatedSupportTicket = customerService.updateSupportTicket(id, supportTicketDetails);
        return ResponseEntity.ok(updatedSupportTicket);
    }
}
