package com.invillia.acme.repository;

import com.invillia.acme.model.db.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}