package com.qorvia.paymentservice.config.rabbitmq;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class RabbitMQConfigProperties {

    @Value("${rabbitmq.queue}")
    private String QUEUE;

    @Value("${rabbitmq.binding.account-service.exchange}")
    private String accountExchange;

    @Value("${rabbitmq.binding.notification-service.exchange}")
    private String notificationExchange;

    @Value("${rabbitmq.binding.blog-service.exchange}")
    private String blogExchange;

    @Value("${rabbitmq.binding.communication-service.exchange}")
    private String communicationExchange;

    @Value("${rabbitmq.binding.event-management-service.exchange}")
    private String eventManagementExchange;

    @Value("${rabbitmq.binding.payment-service.exchange}")
    private String paymentExchange;
}
