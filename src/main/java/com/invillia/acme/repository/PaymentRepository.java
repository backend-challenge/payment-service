package com.invillia.acme.repository;

import com.invillia.acme.model.db.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
