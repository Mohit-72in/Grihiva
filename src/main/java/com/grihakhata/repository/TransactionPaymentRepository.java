package com.grihakhata.repository;

import com.grihakhata.domain.TransactionPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionPaymentRepository extends JpaRepository<TransactionPayment, Long> {
}
