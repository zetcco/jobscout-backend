package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    
}
