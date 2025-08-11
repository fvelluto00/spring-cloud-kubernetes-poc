package com.spring.cloud.k8s.poc.microservice_a.dtos;

public class RequestMessageDto {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
