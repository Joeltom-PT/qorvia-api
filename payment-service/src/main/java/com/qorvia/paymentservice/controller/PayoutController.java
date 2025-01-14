package com.qorvia.paymentservice.controller;

import com.qorvia.paymentservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.paymentservice.service.PayoutService;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PayoutController {

    private final PayoutService payoutService;

    @PostMapping("/onboard-stripe-account")
    public ResponseEntity<String> generateAccountLinkForOnboarding(@RequestBody StripeAccountOnboardingRequest stripeAccountOnboardingRequest) {
        log.info("Requested to get the connecting link");
        try {
            String url = payoutService.connectOrganizerAccount(stripeAccountOnboardingRequest);
            log.info("connecting link generated : {}", url);
            return ResponseEntity.ok(url);
        } catch (StripeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("An unexpected error occurred: " + e.getMessage());
        }
    }



}
