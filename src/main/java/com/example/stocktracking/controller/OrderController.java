package com.example.stocktracking.controller;

import com.example.stocktracking.entity.Orders;
import com.example.stocktracking.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/placeOrder")
    public ResponseEntity<?> placeOrder(@RequestBody Orders orders) {
            Orders savedOrder = orderService.placeOrder(orders);
            return ResponseEntity.ok(savedOrder);

    }






}
