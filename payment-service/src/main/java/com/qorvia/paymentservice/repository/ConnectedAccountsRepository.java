package com.qorvia.paymentservice.repository;

import com.qorvia.paymentservice.model.ConnectedAccounts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectedAccountsRepository extends JpaRepository<ConnectedAccounts, Long> {
    ConnectedAccounts findByOrganizerAccountId(String id);
}
