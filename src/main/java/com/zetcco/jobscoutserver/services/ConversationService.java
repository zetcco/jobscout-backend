package com.zetcco.jobscoutserver.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.ConversationRepository;
import com.zetcco.jobscoutserver.services.mappers.ConversationMapper;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class ConversationService {
    
    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationMapper conversationMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private RTCService rtcService;

    // TODO: Remove duplicates
    public ConversationDTO createConversation(List<Long> participantIds) throws NotFoundException, JsonProcessingException {
        User start_user = userService.getAuthUser();
        participantIds.add(start_user.getId());
        List<User> participants = participantIds.stream().map( (id) -> userService.getUser(id)).collect(Collectors.toList());
        List<String> participantNames = participants.stream().map((participant) -> participant.getDisplayName()).collect(Collectors.toList());
        String convoName = participants.size() == 2 ?  null : String.join(", ", participantNames);
        Conversation conversation = Conversation.builder()
                                                    .participants(participants)
                                                    .name(convoName)
                                                    .build();
        
        conversation = conversationRepository.save(conversation);
        ConversationDTO newConversationDTO = conversationMapper.mapToDto(conversation);
        for (Long userId : participantIds) 
            rtcService.sendToUser(userId, "/messaging/private", "CONVERSATION", newConversationDTO);
            // simpMessagingTemplate.convertAndSend("/messaging/private/" + userId, newConversationDTO);


        return newConversationDTO;
    }

    public Conversation getConversation(Long conversation_id) throws NotFoundException {
        return conversationRepository.findById(conversation_id).orElseThrow(() -> new NotFoundException("Conversation not found"));
    }

    public List<ConversationDTO> getConversations() {
        User user = userService.getAuthUser();
        List<Conversation> conversations = conversationRepository.findByParticipantsId(user.getId());
        return conversationMapper.mapToDtos(conversations);
    }

}
