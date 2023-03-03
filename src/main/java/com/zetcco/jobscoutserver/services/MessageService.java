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
    
    public MessageDTO creatMessage(Long conversation_id, String content) throws NotFoundException, AccessDeniedException {
        Conversation conversation = conversationService.getConversation(conversation_id);
        User sender = userService.getAuthUser();
        if (!conversation.getParticipants().contains(sender))
            throw new AccessDeniedException("You do not have permission to access this conversation");
        Message message = Message.builder()
                                .content(content)
                                .seen(false)
                                .sender(sender)
                                .timestamp(new Date())
                                .conversation(conversation)
                                .build();
        return messageMapper.mapToDto(messageRepository.save(message));
    }

    public List<MessageDTO> getMessages(Long conversation_id, int pageNo, int pageSize) throws NotFoundException {
        Conversation conversation = conversationService.getConversation(conversation_id);
        User requester = userService.getAuthUser();
        if (!conversation.getParticipants().contains(requester))
            throw new AccessDeniedException("You do not have permission to access this conversation");
        PageRequest page = PageRequest.of(pageNo, pageSize);
        List<Message> messages = messageRepository.findByConversationId(conversation_id, page, Sort.by(Sort.Direction.DESC, "timestamp")).getContent();
        return messageMapper.mapToDtos(messages);
    }

    // TODO: Add authorization to this
    public void sendMessage(Long conversation_id, MessageDTO messageDTO) {
        this.simpMessagingTemplate.convertAndSend("/conversation/" + conversation_id, messageDTO);
    }
}
