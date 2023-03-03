package com.zetcco.jobscoutserver.services.mappers;

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
    
}
