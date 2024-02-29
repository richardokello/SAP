package co.ke.spsat.bowip.controller;

import co.ke.spsat.bowip.dtos.CustomerDataRequest;
import co.ke.spsat.bowip.dtos.CustomerResponseData;
import co.ke.spsat.bowip.dtos.ProductResponse;
import co.ke.spsat.bowip.entities.Products;
import co.ke.spsat.bowip.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CustomerController {
    @Autowired
    CustomerService customerService;
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
    public ResponseEntity<List<CustomerDataRequest>>getListOfProducts(@RequestParam(defaultValue = "0") Integer pageNo,
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
}
