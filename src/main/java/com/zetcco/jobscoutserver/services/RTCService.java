package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zetcco.jobscoutserver.domain.support.RTCSignal;

@Service
public class RTCService {
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void sendToUser(RTCSignal signal) {
        // simpMessagingTemplate.convertAndSendToUser(signal.getRecieverId().toString(), "/call", signal);
    }

    public void sendToDestination(Long senderId, String destination, String type, Object payload) throws JsonProcessingException {
        RTCSignal signal = new RTCSignal(senderId, type, objectMapper.writeValueAsString(payload));
        simpMessagingTemplate.convertAndSend(destination, signal);
    }

    public void sendToDestination(Long senderId, String destination, String type) throws JsonProcessingException {
        RTCSignal signal = new RTCSignal(senderId, type, objectMapper.writeValueAsString(null));
        simpMessagingTemplate.convertAndSend(destination, signal);
    }

    public void sendToDestination(String destination, RTCSignal signal) {
        simpMessagingTemplate.convertAndSend(destination, signal);
    }
}
