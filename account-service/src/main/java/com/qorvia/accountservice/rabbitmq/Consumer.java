//package com.qorvia.accountservice.rabbitmq;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.qorvia.accountservice.config.rabbitmq.RabbitMQConfigProperties;
//import com.qorvia.accountservice.dto.SampleMessage;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.amqp.core.Message;
//import org.springframework.stereotype.Service;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Autowired;
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
//
//    @RabbitListener(queues = "qorviaQueue")
//    public void receiveMessage(Message message) {
//        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
//
//        try {
//
//            switch (routingKey) {
//                case "sampleMessage":
//                    SampleMessage sampleMessage = objectMapper.readValue(message.getBody(), SampleMessage.class);
//                    sampleMessagePrint(sampleMessage);
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
//    private void sampleMessagePrint(SampleMessage message) {
//        log.info("Received message: {}", message.getData());
//    }
//}
