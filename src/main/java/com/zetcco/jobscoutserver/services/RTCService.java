package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.RTCSignal;

@Service
public class RTCService {
    
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToUser(RTCSignal signal) {
        simpMessagingTemplate.convertAndSendToUser(signal.getRecieverId().toString(), "/call", signal);
    }
}
