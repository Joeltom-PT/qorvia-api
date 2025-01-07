package com.qorvia.paymentservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payouts")
public class PayoutTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long organizerId;
    private String country;
    private String currency;
    private String accountHolderName;
    private String accountHolderType;
    private String accountNumber;
    private String ifscCode;
    private long amount;
    private long commission;
    private long amountAfterCommission;
    private String payoutId;
    @Enumerated(value = EnumType.STRING)
    private PayoutStatus status;
//    private String errorMessage;

}
