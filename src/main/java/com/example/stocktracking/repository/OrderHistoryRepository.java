package com.example.stocktracking.repository;

import com.example.stocktracking.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory,Integer> {
}
