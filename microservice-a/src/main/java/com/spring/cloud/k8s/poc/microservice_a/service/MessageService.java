package com.spring.cloud.k8s.poc.microservice_a.service;

import com.spring.cloud.k8s.poc.microservice_a.entities.Message;
import com.spring.cloud.k8s.poc.microservice_a.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public Message save(String message, String source) {
        Message newMessage = new Message();
        newMessage.setMessage(message);
        newMessage.setSource(source);

        return messageRepository.save(newMessage);
    }
}
