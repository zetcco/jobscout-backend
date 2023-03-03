package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.services.support.MessageDTO;

@Component
public class MessageMapper {
    
    @Autowired
    private UserMapper userMapper;

    public MessageDTO mapToDto(Message message) {
        MessageDTO messageDTO = MessageDTO.builder()
                                            .id(message.getId())
                                            .content(message.getContent())
                                            .sender(userMapper.mapToDto(message.getSender()))
                                            .timestamp(message.getTimestamp())
                                            .build();
        return messageDTO;
    }

    public List<MessageDTO> mapToDtos(List<Message> messages) {
        return messages.stream().map( message -> this.mapToDto(message) ).collect(Collectors.toList());
    }
    
}
