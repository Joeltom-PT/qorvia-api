package com.qorvia.paymentservice.client;

import com.qorvia.paymentservice.dto.PaymentStatusChangeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "EVENT-MANAGEMENT-SERVICE", url = "http://localhost:8883")

public interface EventManagementClient {

    @PostMapping("/event/bookings/payment-status-update")
    public ResponseEntity<String> paymentStatusUpdate(@RequestBody PaymentStatusChangeDTO paymentStatusChangeDTO);


}
