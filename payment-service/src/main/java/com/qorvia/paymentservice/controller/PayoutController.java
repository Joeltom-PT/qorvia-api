package com.qorvia.paymentservice.controller;

import com.qorvia.paymentservice.dto.request.StripeAccountOnboardingRequest;
import com.qorvia.paymentservice.security.RequireRole;
import com.qorvia.paymentservice.security.Roles;
import com.qorvia.paymentservice.service.PayoutService;
import com.qorvia.paymentservice.service.jwt.JwtService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
@Slf4j
public class PayoutController {

    private final PayoutService payoutService;
    private final JwtService jwtService;





}
