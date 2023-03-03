package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;

@Component
public class ConversationMapper {

    @Autowired
    private UserMapper userMapper;

    public ConversationDTO mapToDto(Conversation conversation) {
        ConversationDTO conversationDTO = ConversationDTO.builder()
                                                .id(conversation.getId())
                                                .participants(userMapper.mapToDtos(conversation.getParticipants()))
                                                .build();
        return conversationDTO;
                                                
    }

    public List<ConversationDTO> mapToDtos(List<Conversation> conversations) {
        return conversations.stream().map( conversation -> this.mapToDto(conversation) ).collect(Collectors.toList());
    }
    
}
