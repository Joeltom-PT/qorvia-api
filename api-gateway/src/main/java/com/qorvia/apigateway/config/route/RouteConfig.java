package com.qorvia.apigateway.config.route;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("account-service", r -> r.path("/account/**")
                        .uri("http://localhost:8880"))
                .route("blog-feedback-service", r -> r.path("/blog-feedback/**")
                        .uri("http://localhost:8881"))
                .route("communication-service", r -> r.path("/communication/**")
                        .uri("http://localhost:8882"))
                .route("event-management-service", r -> r.path("/event/**")
                        .uri("http://localhost:8883"))
                .route("notification-service", r -> r.path("/notification/**")
                        .uri("http://localhost:8884"))
                .route("payment-service", r -> r.path("/payment/**")
                        .uri("http://localhost:8885"))
                .build();
    }
}
