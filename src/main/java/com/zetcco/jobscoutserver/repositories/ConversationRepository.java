package com.zetcco.jobscoutserver.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
}
