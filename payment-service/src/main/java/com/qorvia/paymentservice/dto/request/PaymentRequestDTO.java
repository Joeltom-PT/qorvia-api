package com.qorvia.paymentservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequestDTO {
    private String currency;
    private int amount;
    private String email;
    private EventData eventData;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class EventData {
        private String eventId;
        private String eventName;
        private String imgUrl;
    }
}
