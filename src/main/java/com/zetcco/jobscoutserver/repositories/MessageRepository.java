package com.zetcco.jobscoutserver.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.messaging.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    
}
