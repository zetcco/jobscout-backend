package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.transaction.Transactional;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testSendMessage() {
        Conversation conversation = conversationRepository.findById(1L).orElseThrow();
        List<Message> messages = conversation.getMessages();
        User user = userRepository.findById(115L).orElseThrow();
        Message message = Message.builder()
                                 .conversation(conversation)
                                 .sender(user)
                                 .seen(false)
                                 .timestamp(new Date())
                                 .build();
        messages.add(message);

        messageRepository.save(message);
        conversationRepository.save(conversation);
    }

}
