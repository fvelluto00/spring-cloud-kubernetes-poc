package com.spring.cloud.k8s.poc.microservice_a.controllers;

import com.spring.cloud.k8s.poc.microservice_a.dtos.RequestMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/messages")
public class MessagesController {
    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    /**
     * Endpoint to receive messages and simply log them.
     *
     * @param body the request body containing the message
     */
    @PostMapping
    public void postMessage(
            @RequestBody RequestMessageDto body
    ) {
        logger.info("Received message: {}", body.getMessage());
    }

    /**
     * Endpoint to receive messages and forward them to Microservice B.
     *
     * @param body the request body containing the message
     */
    @PostMapping("/to-microservice-b")
    public void postMessageToMicroserviceB(
            @RequestBody RequestMessageDto body
    ) {
        logger.info("Received message for Microservice B: {}", body.getMessage());
    }
}
