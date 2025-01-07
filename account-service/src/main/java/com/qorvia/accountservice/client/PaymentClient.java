package com.qorvia.accountservice.client;

import com.qorvia.accountservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.accountservice.dto.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8885")
public interface PaymentClient {

    @PostMapping("/payment/onboard-stripe-account")
    public ResponseEntity<String> generateAccountLinkForOnboarding(@RequestBody StripeAccountOnboardingRequest stripeAccountOnboardingRequest);

}
