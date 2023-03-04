package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.MessageRepository;
import com.zetcco.jobscoutserver.services.mappers.MessageMapper;
import com.zetcco.jobscoutserver.services.support.MessageDTO;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

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
    private SimpMessagingTemplate simpMessagingTemplate;

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
    public void sendMessage(Long conversation_id, MessageDTO message) {
        Conversation conversation = conversationService.getConversation(conversation_id);
        List<User> participants = conversation.getParticipants();
        if (participants.contains(userService.getUser(message.getSenderId()))) {
            Message newMessage = Message.builder()
                                    .content(message.getContent())
                                    .sender(User.builder().id(message.getSenderId()).build())
                                    .conversation(Conversation.builder().id(message.getConversationId()).build())
                                    .timestamp(new Date())
                                    .build();
            newMessage = messageRepository.save(newMessage);
            MessageDTO newMessageDTO = messageMapper.mapToDto(newMessage);
            for (User participant : conversation.getParticipants()) 
                this.simpMessagingTemplate.convertAndSend("/messaging/private/" + participant.getId(), newMessageDTO);
        }
        else
            throw new AccessDeniedException("User " + message.getSenderId() + " do not have permission to Conversation " + conversation_id);
    }
}
