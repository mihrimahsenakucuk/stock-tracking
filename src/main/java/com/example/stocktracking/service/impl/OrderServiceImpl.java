package com.example.stocktracking.service.impl;

import com.example.stocktracking.entity.*;
import com.example.stocktracking.repository.OrderDetailRepository;
import com.example.stocktracking.repository.OrderHistoryRepository;
import com.example.stocktracking.repository.OrderRepository;
import com.example.stocktracking.repository.ProductRepository;
import com.example.stocktracking.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final ProductRepository productRepository;

    private final OrderHistoryRepository orderHistoryRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository, ProductRepository productRepository, OrderHistoryRepository orderHistoryRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productRepository = productRepository;
        this.orderHistoryRepository = orderHistoryRepository;
    }


    @Transactional
    @Override
    public Orders placeOrder(Orders orders) {
        double totalPrice = 0;
        String orderCode = getOrderForCode();
        orders.setOrderCode(orderCode);

        for (OrderDetail orderDetail : orders.getDetails()) {
            Product product = productRepository.findById(orderDetail.getProduct().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + orderDetail.getProduct().getId()));

            if (product.getStockQuantity() < orderDetail.getQuantityInOrder()) {
                throw new IllegalArgumentException("Yeterli stok yok: " + product.getName() + ", Sepetinizdeki adet: " + orderDetail.getQuantityInOrder());
            }

            int newStock = product.getStockQuantity() - orderDetail.getQuantityInOrder();
            product.setStockQuantity(newStock);
            productRepository.save(product);

            double unitPrice = orderDetail.getUnitPrice() != 0 ? orderDetail.getUnitPrice() : product.getPrice();

            totalPrice += unitPrice * orderDetail.getQuantityInOrder();

            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setOrder(orders);
            orderHistory.setProduct(product);
            orderHistory.setTotalPrice(unitPrice);
            orderHistory.setQuantity(orderDetail.getQuantityInOrder());
            orderHistory.setTotalPrice(unitPrice * orderDetail.getQuantityInOrder());
            orderHistoryRepository.save(orderHistory);
        }
        orders.setTotalPrice(totalPrice);

        if (orders.getCreatedDate() == null) {
            orders.setCreatedDate(LocalDateTime.now());
        }
        Orders savedOrder = orderRepository.save(orders);
        return savedOrder;
    }

    @Override
    public String getOrderForCode() {
        String orderCode = "COD-" + UUID.randomUUID().toString();
        return orderCode;
    }


    @Override
    public String getAllOrdersForCustomer(int orderId, Customer customer) {
        return null;
    }


}
