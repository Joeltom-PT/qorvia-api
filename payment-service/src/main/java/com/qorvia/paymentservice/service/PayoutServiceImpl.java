package com.qorvia.paymentservice.service;

import com.qorvia.paymentservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.paymentservice.model.AccountStatus;
import com.qorvia.paymentservice.model.ConnectedAccounts;
import com.qorvia.paymentservice.repository.ConnectedAccountsRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayoutServiceImpl implements PayoutService {

    private final ConnectedAccountsRepository connectedAccountsRepository;

    @Override
    public String connectOrganizerAccount(StripeAccountOnboardingRequest stripeAccountOnboardingRequest) throws StripeException {
        Map<String, Object> accountParams = new HashMap<>();
        accountParams.put("type", "express");
        accountParams.put("country", "US");
        accountParams.put("email", stripeAccountOnboardingRequest.getEmail());

        Account account = Account.create(accountParams);
        String organizerAccountId = account.getId();

        Map<String, Object> accountLinkParams = new HashMap<>();
        accountLinkParams.put("account", organizerAccountId);
        accountLinkParams.put("refresh_url", "https://your-app.com/reauth?organizerId=" + stripeAccountOnboardingRequest.getOrganizerId());
        accountLinkParams.put("return_url", "https://your-app.com/success?organizerId=" + stripeAccountOnboardingRequest.getOrganizerId());
        accountLinkParams.put("type", "account_onboarding");

        AccountLink accountLink = AccountLink.create(accountLinkParams);

        ConnectedAccounts connectedAccount = new ConnectedAccounts();
        connectedAccount.setOrganizerEmail(stripeAccountOnboardingRequest.getEmail());
        connectedAccount.setOrganizerAccountId(organizerAccountId);
        connectedAccount.setOrganizerId(stripeAccountOnboardingRequest.getOrganizerId());
        connectedAccount.setAccountStatus(AccountStatus.PENDING);
        connectedAccountsRepository.save(connectedAccount);

        return accountLink.getUrl();
    }

    @Override
    public void handleAccountUpdate(Account account) {
        boolean isChargesEnabled = account.getChargesEnabled();
        boolean isPayoutsEnabled = account.getPayoutsEnabled();
        String accountStatus = "pending";
        if (isChargesEnabled && isPayoutsEnabled) {
            accountStatus = "active";
        } else if (!isChargesEnabled) {
            accountStatus = "inactive";
        } else if (!isPayoutsEnabled) {
            accountStatus = "pending";
        }
        ConnectedAccounts connectedAccount = connectedAccountsRepository.findByOrganizerAccountId(account.getId());

        if (connectedAccount != null) {
            if ("active".equals(accountStatus)) {
                connectedAccount.setAccountStatus(AccountStatus.ACTIVE);
                log.info("Account {} is now active!", account.getId());
            } else if ("inactive".equals(accountStatus)) {
                connectedAccount.setAccountStatus(AccountStatus.INACTIVE);
                log.warn("Account {} is now inactive!", account.getId());
            } else if ("pending".equals(accountStatus)) {
                connectedAccount.setAccountStatus(AccountStatus.PENDING);
                log.warn("Account {} is in pending status.", account.getId());
            } else {
                log.error("Unhandled account status: {}", accountStatus);
            }

            connectedAccountsRepository.save(connectedAccount);
        } else {
            log.error("Connected account not found for Stripe account ID: {}", account.getId());
        }
    }


}
