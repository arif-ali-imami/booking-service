package com.echoItSolution.booking_service.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BookingConfig {

    @Bean
    @LoadBalanced
    @SuppressWarnings("uncheck")
    public RestTemplate restTemplateConfig(){
        return new RestTemplate();
    }
}
