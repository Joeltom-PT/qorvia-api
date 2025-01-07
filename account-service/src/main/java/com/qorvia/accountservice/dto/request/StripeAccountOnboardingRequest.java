package com.qorvia.accountservice.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StripeAccountOnboardingRequest {
    private Long organizerId;
    private String email;
}
