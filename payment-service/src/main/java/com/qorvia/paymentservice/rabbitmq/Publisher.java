//package com.qorvia.paymentservice.rabbitmq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qorvia.paymentservice.config.rabbitmq.RabbitMQConfigProperties;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.connection.CorrelationData;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class Publisher {
//
//    private final RabbitTemplate rabbitTemplate;
//    private final RabbitMQConfigProperties configProperties;
//    private final ObjectMapper objectMapper;
//
//    public void sendToEventService(String routingKey, Object message) {
//        try {
//            log.debug("Preparing to send message to event service with routing key: {}", routingKey);
//            byte[] serializedMessage = objectMapper.writeValueAsBytes(message);
//            CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
//
//            rabbitTemplate.convertAndSend(
//                    configProperties.getEventManagementExchange(),
//                    routingKey,
//                    serializedMessage,
//                    correlationData
//            );
//
//            log.debug("Message sent to RabbitMQ, awaiting confirmation with correlation id: {}", correlationData.getId());
//            correlationData.getFuture().whenComplete((result, ex) -> {
//                if (ex == null && result != null && result.isAck()) {
//                    log.info("Message successfully sent to RabbitMQ with correlation id: {}", correlationData.getId());
//                } else {
//                    log.error("Message failed to send to RabbitMQ with correlation id: {}. Reason: {}", correlationData.getId(), result != null ? result.getReason() : ex.getMessage());
//                }
//            });
//
//        } catch (IOException e) {
//            log.error("Failed to serialize message for RabbitMQ", e);
//        } catch (Exception e) {
//            log.error("Unexpected error occurred while sending message to RabbitMQ", e);
//        }
//    }
//}