package com.qorvia.paymentservice.service;

import com.qorvia.paymentservice.dto.request.StripeAccountOnboardingRequest;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;

public interface PayoutService {
    String connectOrganizerAccount(StripeAccountOnboardingRequest stripeAccountOnboardingRequest) throws StripeException;

    void handleAccountUpdate(Account account);
}
