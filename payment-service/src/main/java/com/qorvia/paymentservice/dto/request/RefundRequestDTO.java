package com.qorvia.paymentservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RefundRequestDTO {
    private String sessionId;
    private int refundPercentage;
}
