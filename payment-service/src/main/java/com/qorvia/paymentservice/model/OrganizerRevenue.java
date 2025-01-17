package com.qorvia.paymentservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "organizer_revenue")
public class OrganizerRevenue {

    @Id
    private Long id;

    private Long organizerId;

    private Long pendingAmount;

    private Long totalTransferred;

    @Column(nullable = false)
    private Long totalEarned;

}
