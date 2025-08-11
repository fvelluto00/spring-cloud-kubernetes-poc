package com.spring.cloud.k8s.poc.microservice_a.repositories;

import com.spring.cloud.k8s.poc.microservice_a.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
