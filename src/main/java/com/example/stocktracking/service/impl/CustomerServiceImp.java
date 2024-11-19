package com.example.stocktracking.service.impl;

import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.repository.CustomerRepository;
import com.example.stocktracking.service.CustomerService;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImp implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImp(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
}
