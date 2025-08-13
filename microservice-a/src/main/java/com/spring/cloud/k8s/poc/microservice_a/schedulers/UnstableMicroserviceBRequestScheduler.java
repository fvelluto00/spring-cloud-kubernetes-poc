package com.spring.cloud.k8s.poc.microservice_a.schedulers;

import com.spring.cloud.k8s.poc.microservice_a.dtos.RequestMessageDto;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UnstableMicroserviceBRequestScheduler {
    private static final Logger logger = LoggerFactory.getLogger(UnstableMicroserviceBRequestScheduler.class);

    @Autowired
    private RestTemplate restTemplate;

    @CircuitBreaker(name = "unstableMicroserviceB", fallbackMethod = "fallback_callUnstableMicroserviceB")
    public String callUnstableMicroserviceB() {
        String microServiceBMessagesPostAPI = "http://microservice-b:8080/messages/unstable";
        RequestMessageDto requestMessageDto = new RequestMessageDto();
        requestMessageDto.setMessage("test");

        return restTemplate.postForEntity(
                microServiceBMessagesPostAPI,
                requestMessageDto,
                String.class
        ).getBody();
    }

    public String fallback_callUnstableMicroserviceB(Throwable t) {
        logger.warn("Fallback triggered: {}", t.toString());
        return "Fallback response";
    }

    @Scheduled(fixedRate = 2000)
    public void sendRequest() {
        logger.info("Sending request to unstable Microservice B...");
        String response = callUnstableMicroserviceB();
        logger.info("Response: {}", response);
    }
}