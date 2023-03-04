package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;
import com.zetcco.jobscoutserver.services.ConversationService;
import com.zetcco.jobscoutserver.services.MessageService;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;
import com.zetcco.jobscoutserver.services.support.MessageDTO;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Controller
@RequestMapping("/messaging")
public class MessagingController {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MessageService messagingService;

    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/create")
    public ResponseEntity<ConversationDTO> createConversation(@RequestBody Map<String, Long> request) {
        try {
            Long end_user_id = request.get("id");
            return new ResponseEntity<ConversationDTO>(conversationService.createConversation(end_user_id), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ConversationDTO>> getConversations() {
        return new ResponseEntity<List<ConversationDTO>>(conversationService.getConversations(), HttpStatus.OK);
    }

    @GetMapping("/{conversationId}")
    public ResponseEntity<List<MessageDTO>> getMessages(@PathVariable Long conversationId, @RequestParam("page") int page, @RequestParam("count") int count) {
        try {
            return new ResponseEntity<List<MessageDTO>>(messagingService.getMessages(conversationId, page, count), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @MessageMapping("/messaging/{conversationId}")
    public void sendMessage(@DestinationVariable Long conversationId, Message<RTCSignal> signal) {
        try {
            RTCSignal payload = signal.getPayload();
            MessageDTO message = objectMapper.readValue(payload.getData(), MessageDTO.class);
            messagingService.sendMessage(conversationId, message);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }
    
}
