package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.core.env.Environment;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class ConversationMapper {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Environment environment;

    public ConversationDTO mapToDto(Conversation conversation) {
        final String RESOURCE_URL = conversation.getPicture() == null ? null : environment.getProperty("server.url") + "/media/file/" + conversation.getPicture();
        ConversationDTO conversationDTO = ConversationDTO.builder()
                                                .id(conversation.getId())
                                                .name(conversation.getName())
                                                .picture(RESOURCE_URL)
                                                .participants(userMapper.mapToDtos(conversation.getParticipants()))
                                                .build();
        return conversationDTO;
                                                
    }

    public List<ConversationDTO> mapToDtos(List<Conversation> conversations) {
        return conversations.stream().map( conversation -> this.mapToDto(conversation) ).collect(Collectors.toList());
    }
    
}
