package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

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
    private SimpMessagingTemplate simpMessagingTemplate;

    public ConversationDTO createConversation(Long end_user_id) throws NotFoundException {
        User end_user = userService.getUser(end_user_id);
        User start_user = userService.getAuthUser();
        Conversation conversation = Conversation.builder().participants(List.of(start_user, end_user)).build();
        ConversationDTO conversationDTO = conversationMapper.mapToDto(conversationRepository.save(conversation));
        simpMessagingTemplate.convertAndSend("/messaging/private/" + end_user_id, conversationDTO);
        return conversationDTO;
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
