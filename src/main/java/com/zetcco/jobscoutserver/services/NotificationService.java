package com.zetcco.jobscoutserver.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Notification;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void sendToUser(Notification notification) {
        System.out.println(notification);
        simpMessagingTemplate.convertAndSendToUser(notification.getUser().getId().toString(), "/notify", notification);
    }

    public void sendToAll(Notification notification) {
        simpMessagingTemplate.convertAndSend("/all/notify", notification);
    }
    
}
