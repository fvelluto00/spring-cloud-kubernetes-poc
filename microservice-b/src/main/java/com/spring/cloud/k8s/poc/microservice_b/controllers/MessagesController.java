package com.spring.cloud.k8s.poc.microservice_b.controllers;

import com.spring.cloud.k8s.poc.microservice_b.dtos.RequestMessageDto;
import com.spring.cloud.k8s.poc.microservice_b.configs.MicroserviceAConfig;
import com.spring.cloud.k8s.poc.microservice_b.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value = "/messages")
@RestController
public class MessagesController {
    private final Logger logger = LoggerFactory.getLogger(MessagesController.class);

    @Autowired
    private MicroserviceAConfig config;
    @Autowired
    private MessageService messageService;

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
}
