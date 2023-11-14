package com.foodhub.controller;

import com.foodhub.dto.CustomerDTO;
import com.foodhub.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/customer")
@CrossOrigin("http://localhost:3000/")
@RestController
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @GetMapping
    public ResponseEntity<?> getAllCustomers() {
        return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
    }

    @GetMapping("/{customerId}") // get customer by Customer Id-----------
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId){
        return new ResponseEntity<>(customerService.getCustomerById(customerId), HttpStatus.OK) ;
    }

    @PutMapping // Update Customer Details -----------------
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerDTO customer){
        return new ResponseEntity<>(customerService.updateCustomerDetails(customer), HttpStatus.OK) ;
    }

    @DeleteMapping("/{customerId}") // Delete Customer ----------------------
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long customerId){
        return new ResponseEntity<>(customerService.deleteCustomerById(customerId), HttpStatus.OK ) ;
    }

    @PatchMapping("/block/")
    public ResponseEntity<?> changeBlockingStatus(@RequestBody CustomerDTO customer){
        System.out.println("In change blocking status");
        return new ResponseEntity<> (customerService.changeBlockingStatus(customer.getId()), HttpStatus.OK) ;
    }

}
