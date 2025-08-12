package com.spring.cloud.k8s.poc.microservice_a.controllers;

import com.spring.cloud.k8s.poc.microservice_a.dtos.RequestMessageDto;
import com.spring.cloud.k8s.poc.microservice_a.configs.MicroserviceAConfig;
import com.spring.cloud.k8s.poc.microservice_a.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RequestMapping(value = "/messages")
@RestController
public class MessagesController {
    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    private MicroserviceAConfig config;
    @Autowired
    private MessageService messageService;
    @Autowired
    private RestTemplate restTemplate;

    /**
     * Return a welcome message.
     *
     * @return welcome message
     */
    @GetMapping
    public String getWelcomeMessage() {
        return config.getMessage();
    }

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
        messageService.save(body.getMessage(), "LOG_ONLY_ENDPOINT");
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
        messageService.save(body.getMessage(), "TO_MICROSERVICE_B_ENDPOINT");

        logger.info("Sending message to Microservice B");

        String microServiceBMessagesPostAPI = "http://microservice-b:8080/messages";
        restTemplate.postForEntity(
                microServiceBMessagesPostAPI,
                body,
                String.class
        );

        logger.info("Message sent to Microservice B");
    }
}
