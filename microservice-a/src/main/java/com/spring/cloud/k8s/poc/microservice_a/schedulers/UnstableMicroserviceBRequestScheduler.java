package com.spring.cloud.k8s.poc.microservice_a.schedulers;

import com.spring.cloud.k8s.poc.microservice_a.service.UnstableMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UnstableMicroserviceBRequestScheduler {
    private static final Logger logger = LoggerFactory.getLogger(UnstableMicroserviceBRequestScheduler.class);

    @Autowired
    private UnstableMessageService unstableMessageService;

    @Scheduled(fixedRate = 2000)
    public void sendRequest() {
        logger.info("Sending request to unstable Microservice B...");
        String response = unstableMessageService.callUnstableMicroserviceB();
        logger.info("Response: {}", response);
    }
}