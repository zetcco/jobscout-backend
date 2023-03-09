package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;

@SpringBootTest
public class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSendMessage() {
        Conversation conversation = conversationRepository.findById(1L).orElseThrow();
        User sender = userRepository.findById(105L).orElseThrow();
        Message message = Message.builder()
                                .conversation(conversation)
                                .content("Same as you bro! Doing great")
                                .timestamp(new Date())
                                .sender(sender)
                                .build();
        messageRepository.save(message);
    }

    @Test
    public void testGetConversationMessages() {
        System.out.println("--------------------");
        PageRequest page = PageRequest.of(0, 1);
        List<Message> messages = messageRepository.findByConversationId(1L, page, Sort.by(Sort.Direction.DESC, "timestamp")).getContent();
        System.out.println(messages);
        System.out.println("--------------------");
        page = PageRequest.of(1, 1);
        messages = messageRepository.findByConversationId(1L, page, Sort.by(Sort.Direction.DESC, "timestamp")).getContent();
        System.out.println(messages);
        System.out.println("--------------------");
        page = PageRequest.of(2, 1);
        messages = messageRepository.findByConversationId(1L, page, Sort.by(Sort.Direction.DESC, "timestamp")).getContent();
        System.out.println(messages);
        System.out.println("--------------------");
    }

}
