package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.ConversationRepository;
import com.zetcco.jobscoutserver.repositories.MessageRepository;
import com.zetcco.jobscoutserver.services.mappers.MessageMapper;
import com.zetcco.jobscoutserver.services.support.DeleteMessageDTO;
import com.zetcco.jobscoutserver.services.support.MessageDTO;
import com.zetcco.jobscoutserver.services.support.TypingDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private RTCService rtcService;

    @Autowired
    private ConversationRepository conversationRepository;

    public List<MessageDTO> getMessages(Long conversation_id, int pageNo, int pageSize) throws NotFoundException {
        Conversation conversation = conversationService.getConversation(conversation_id);
        User requester = userService.getAuthUser();
        if (!conversation.getParticipants().contains(requester))
            throw new AccessDeniedException("You do not have permission to access this conversation");
        PageRequest page = PageRequest.of(pageNo, pageSize);

        // TODO: Find a way to get just User ID's rather than whole User objects to reduce JOINS (possibly custom queries, explore that)
        List<Message> messages = messageRepository.findByConversationId(conversation_id, page, Sort.by(Sort.Direction.DESC, "timestamp")).getContent();

        return messageMapper.mapToDtos(messages);
    }

    // TODO: Add authentication to this so that SenderID and jwtToken ID can be cross checked (may be check the Websocket filter)
    public void sendMessage(Long conversation_id, MessageDTO message) throws JsonProcessingException {
        Conversation conversation = conversationService.getConversation(conversation_id);
        List<User> participants = conversation.getParticipants();
        User sender = userService.getUser(message.getSenderId());
        if (participants.contains(sender)) {
            conversation.setSeenUsers(List.of(sender));
            conversationRepository.save(conversation);
            Message newMessage = Message.builder()
                                    .content(message.getContent())
                                    .sender(User.builder().id(message.getSenderId()).build())
                                    .conversation(Conversation.builder().id(conversation_id).build())
                                    .timestamp(new Date())
                                    .build();
            newMessage = messageRepository.save(newMessage);
            MessageDTO newMessageDTO = messageMapper.mapToDto(newMessage);
            for (User participant : conversation.getParticipants()) 
                rtcService.sendToDestination(participant.getId(), "/messaging/private/" + participant.getId().toString(), "MESSAGE", newMessageDTO);
        }
        else
            throw new AccessDeniedException("User " + message.getSenderId() + " do not have permission to Conversation " + conversation_id);
    }

    public void sendTyping(Long conversationId, Long senderId) throws JsonProcessingException {
        Conversation conversation = conversationService.getConversation(conversationId);
        List<User> participants = conversation.getParticipants();
        User sender = userService.getUser(senderId);
        if (participants.contains(sender)) {
            TypingDTO typingDTO = new TypingDTO(conversationId, sender.getDisplayName());
            for (User participant : conversation.getParticipants()) 
                if (participant.getId() != senderId)
                    rtcService.sendToDestination(participant.getId(), "/messaging/private/" + participant.getId().toString(), "TYPING", typingDTO);
        }
        
    }

    public void sendDelete(Long conversationId, Long senderId, DeleteMessageDTO messageDto) throws JsonProcessingException {
        Conversation conversation = conversationService.getConversation(conversationId);
        List<User> participants = conversation.getParticipants();
        User sender = userService.getUser(senderId);
        messageRepository.deleteById(messageDto.getMessageId());
        if (participants.contains(sender)) {
            DeleteMessageDTO deleteMessageDTO = new DeleteMessageDTO(conversationId, messageDto.getMessageId());
            for (User participant : conversation.getParticipants()) 
                rtcService.sendToDestination(participant.getId(), "/messaging/private/" + participant.getId().toString(), "DELETE", deleteMessageDTO);
        }
        
    }
}
