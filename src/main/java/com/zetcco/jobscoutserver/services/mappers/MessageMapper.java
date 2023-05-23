package com.zetcco.jobscoutserver.services.mappers;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.services.support.MessageDTO;

@Component
public class MessageMapper {

    public MessageDTO mapToDto(Message message) {
        MessageDTO messageDTO = MessageDTO.builder()
                                            .id(message.getId())
                                            .content(message.getContent())
                                            .senderId(message.getSender().getId())
                                            .conversationId(message.getConversation().getId())
                                            .timestamp(message.getTimestamp())
                                            .build();
        return messageDTO;
    }

    public List<MessageDTO> mapToDtos(List<Message> messages) {
        return messages.stream().map( message -> this.mapToDto(message) ).collect(Collectors.toList());
    }

    public Message mapToEntity(MessageDTO messageDto) {
        Message newMessage = Message.builder()
                                .content(messageDto.getContent())
                                .sender(User.builder().id(messageDto.getSenderId()).build())
                                .conversation(Conversation.builder().id(messageDto.getConversationId()).build())
                                .timestamp(new Date())
                                .build();
        return newMessage;
    }
    
}
