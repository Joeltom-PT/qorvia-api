package com.qorvia.paymentservice.service;

import com.qorvia.paymentservice.dto.AccountDTO;
import com.qorvia.paymentservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.paymentservice.model.AccountStatus;
import com.qorvia.paymentservice.model.ConnectedAccounts;
import com.qorvia.paymentservice.repository.ConnectedAccountsRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import com.stripe.model.AccountLink;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayoutServiceImpl implements PayoutService {




}
