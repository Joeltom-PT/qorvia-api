package com.qorvia.eventmanagementservice.clients;

import com.qorvia.eventmanagementservice.dto.PaymentInfoDTO;
import com.qorvia.eventmanagementservice.dto.PaymentRequestDTO;
import com.qorvia.eventmanagementservice.dto.RefundDTO;
import com.qorvia.eventmanagementservice.dto.RefundRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "PAYMENT-SERVICE", url = "http://localhost:8885")
public interface PaymentClient {
    @PostMapping("/payment/create-checkout-session")
    ResponseEntity<PaymentInfoDTO> getPaymentInfo(PaymentRequestDTO paymentRequestDTO);

    @PostMapping("/payment/payment-refund")
    public ResponseEntity<RefundDTO> paymentRefundRequest(@RequestBody RefundRequestDTO refundRequest);

}
