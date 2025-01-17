//package com.qorvia.eventmanagementservice.config.rabbitmq;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.TopicExchange;
//import org.springframework.amqp.rabbit.annotation.EnableRabbit;
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.amqp.support.converter.SimpleMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.converter.ByteArrayMessageConverter;
//
//@Configuration
//@EnableRabbit
//@RequiredArgsConstructor
//public class RabbitMQConfig {
//
//    private final RabbitMQConfigProperties configProperties;
//
//    @Bean
//    public Queue queue() {
//        return new Queue(configProperties.getQUEUE(), true);
//    }
//
////     To send account service
//    @Bean
//    public TopicExchange accountServiceExchange() {
//        return new TopicExchange(configProperties.getAccountExchange());
//    }
//
//    @Bean
//    public Binding accountServiceBinding() {
//        return BindingBuilder.bind(queue()).to(accountServiceExchange()).with("#");
//    }
//
//
//    // To receive data
//    @Bean
//    public TopicExchange eventManagementServiceExchange() {
//        return new TopicExchange(configProperties.getEventManagementExchange());
//    }
//
//    @Bean
//    public Binding eventManagementServiceBinding() {
//        return BindingBuilder.bind(queue()).to(eventManagementServiceExchange()).with("#");
//    }
//
//
//    @Bean
//    public MessageConverter messageConverter() {
//        return new SimpleMessageConverter();
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(messageConverter());
//        return rabbitTemplate;
//    }
//}
