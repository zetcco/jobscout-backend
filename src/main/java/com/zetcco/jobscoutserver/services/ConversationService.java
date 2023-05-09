package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zetcco.jobscoutserver.domain.messaging.Conversation;
import com.zetcco.jobscoutserver.domain.messaging.Message;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.ConversationRepository;
import com.zetcco.jobscoutserver.repositories.MessageRepository;
import com.zetcco.jobscoutserver.services.mappers.ConversationMapper;
import com.zetcco.jobscoutserver.services.mappers.MessageMapper;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;
import com.zetcco.jobscoutserver.services.support.MessageDTO;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

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

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageMapper messageMapper;

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
        newConversationDTO.setRead(false);
        for (Long userId : participantIds) 
            rtcService.sendToDestination(userId, "/messaging/private/" + userId, "CONVERSATION", newConversationDTO);
            // simpMessagingTemplate.convertAndSend("/messaging/private/" + userId, newConversationDTO);


        return newConversationDTO;
    }

    public Conversation getConversation(Long conversation_id) throws NotFoundException {
        return conversationRepository.findById(conversation_id).orElseThrow(() -> new NotFoundException("Conversation not found"));
    }

    public List<ConversationDTO> getConversations() {
        User user = userService.getAuthUser();
        List<Conversation> conversations = conversationRepository.findByParticipantsId(user.getId());
        List<ConversationDTO> conversationDTOs = new ArrayList<>();
        for (Conversation conversation : conversations) {
            Boolean read = conversation.getSeenUsers().contains(user);
            ConversationDTO conversationDTO = conversationMapper.mapToDto(conversation);
            conversationDTO.setRead(read);
            PageRequest page = PageRequest.of(0, 1);
            List<Message> messages = messageRepository.findByConversationId(conversation.getId(), page, Sort.by(Direction.DESC, "timestamp")).getContent();
            List<MessageDTO> messageDTOs = messageMapper.mapToDtos(messages);
            conversationDTO.setMessages(messageDTOs);
            conversationDTOs.add(conversationDTO);
        }
        return conversationDTOs;
    }


    public void updateConversation(Long conversation_id, String name, String fileName) throws JsonProcessingException, AccessDeniedException {
        User user = userService.getAuthUser();
        Conversation conversation = this.getConversation(conversation_id);
        if (conversation.getParticipants().contains(user)) {
            conversation.setName(name);
            conversation.setPicture(fileName);
            conversation = conversationRepository.save(conversation);
            ConversationDTO conversationDTO = conversationMapper.mapToDto(conversation);
            for (ProfileDTO participant : conversationDTO.getParticipants()) 
                rtcService.sendToDestination(participant.getId(), "/messaging/private/" + participant.getId(), "CONVERSATION_UPDATE", conversationDTO);
        } else {
            throw new AccessDeniedException("You do not have permission to do this action");
        }
    }

    public void markAsRead(Long conversationId) throws AccessDeniedException {
        User user = userService.getAuthUser();
        Conversation conversation = this.getConversation(conversationId);
        if (conversation.getParticipants().contains(user)) {
            List<User> seenUsers = conversation.getSeenUsers();
            seenUsers.add(user);
            conversation.setSeenUsers(seenUsers);
            conversationRepository.save(conversation);
        } else {
            throw new AccessDeniedException("You do not have permission to do this action");
        }
    }
}
