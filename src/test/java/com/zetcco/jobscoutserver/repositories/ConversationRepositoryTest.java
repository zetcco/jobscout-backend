package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ConversationRepositoryTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testStartConversation() {
        Conversation conversation = new Conversation();
        User user1 = userRepository.findById(95L).orElseThrow();
        User user2 = userRepository.findById(63L).orElseThrow();
        conversation.setParticipants(List.of(user1, user2));
        conversationRepository.save(conversation);
    }

    @Test
    @Transactional
    public void testGetConversation() {
        Conversation conversation = conversationRepository.findById(3L).orElseThrow();
        System.out.println(conversation);
    }

    @Test
    public void testGetConversationByParticipants() {
        List<Conversation> conversations = conversationRepository.findByParticipantsId(95L);
        System.out.println(conversations);
    }

}
