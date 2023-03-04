package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.services.mappers.ConversationMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
public class ConversationRepositoryTest {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    @Test
    public void testStartConversation() {
        // Input from the user
        List<Long> participantIds = List.of(51L, 105L, 70L, 89L);

        // Service layer functionality
        List<User> participants = participantIds.stream().map( (id) -> userRepository.findById(id).orElseThrow() ).collect(Collectors.toList());
        List<String> participantNames = participants.stream().map((participant) -> participant.getDisplayName()).collect(Collectors.toList());
        String convoName = participants.size() == 2 ?  null : String.join(", ", participantNames);
        Conversation conversation = Conversation.builder()
                                                    .participants(participants)
                                                    .name(convoName)
                                                    .build();

        conversation = conversationRepository.save(conversation);
        System.out.println(conversationMapper.mapToDto(conversation));
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
