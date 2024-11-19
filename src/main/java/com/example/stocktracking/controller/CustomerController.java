package com.example.stocktracking.controller;

import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.service.CustomerService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/addCustomers")
    public Customer addCustomers(@RequestBody Customer customer){
        return customerService.addCustomer(customer);
    }



}
