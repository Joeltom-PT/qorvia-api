package com.qorvia.paymentservice.repository;

import com.qorvia.paymentservice.model.PayoutTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayoutTransactionRepository extends JpaRepository<PayoutTransaction,Long> {
}
