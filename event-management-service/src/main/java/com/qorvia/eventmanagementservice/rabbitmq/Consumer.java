//package com.qorvia.eventmanagementservice.rabbitmq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qorvia.eventmanagementservice.dto.PaymentStatusChangeDTO;
//import com.qorvia.eventmanagementservice.service.BookingService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class Consumer {
//
//
//    private final ObjectMapper objectMapper;
//    private final BookingService bookingService;
//
//    @RabbitListener(queues = "qorviaQueue")
//    public void receiveMessage(Message message) {
//        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
//
//        try {
//
//            switch (routingKey) {
//                case "paymentStatusChange":
//                    PaymentStatusChangeDTO paymentStatusChangeDTO = objectMapper.readValue(message.getBody(), PaymentStatusChangeDTO.class);
//                    handlePaymentStatusChange(paymentStatusChangeDTO);
//                    break;
//                default:
//                    log.warn("Unknown routing key: {}", routingKey);
//                    break;
//            }
//        } catch (IOException e) {
//            log.error("Failed to deserialize message", e);
//        }
//    }
//
//    private void handlePaymentStatusChange(PaymentStatusChangeDTO paymentStatusChangeDTO) {
//        log.info("updating payment status message to the the event management service with status : {} and sessionId : {}", paymentStatusChangeDTO.getPaymentStatus(), paymentStatusChangeDTO.getPaymentSessionId());
//
//        bookingService.paymentStatusChangeHandle(paymentStatusChangeDTO);
//    }
//}
