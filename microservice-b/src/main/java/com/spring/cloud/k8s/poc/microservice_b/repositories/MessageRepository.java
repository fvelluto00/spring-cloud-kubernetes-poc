package com.spring.cloud.k8s.poc.microservice_b.repositories;

import com.spring.cloud.k8s.poc.microservice_b.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
