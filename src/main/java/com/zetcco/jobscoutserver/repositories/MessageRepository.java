package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.messaging.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Page<Message> findByConversationId(Long id, PageRequest pageRequest, Sort sort);
}
