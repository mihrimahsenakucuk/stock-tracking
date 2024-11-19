package com.example.stocktracking.service;

import com.example.stocktracking.entity.Customer;
import com.example.stocktracking.entity.Orders;
import jakarta.persistence.criteria.Order;

public interface OrderService {

    Orders placeOrder(Orders order); // siparis veriyor
    String getOrderForCode();
    String getAllOrdersForCustomer(int orderId, Customer customer);

}
