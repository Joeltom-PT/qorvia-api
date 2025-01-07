package com.qorvia.eventmanagementservice.dto;

import lombok.Data;

@Data
public class PaymentInfoDTO {
    private String paymentUrl;
    private String sessionId;
    private String tempSessionId;
}
