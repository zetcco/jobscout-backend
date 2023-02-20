package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.repositories.NotificationRepository;
import com.zetcco.jobscoutserver.services.auth.AuthenticationService;
import com.zetcco.jobscoutserver.services.support.NotificationDTO;

@Service
public class NotificationService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper modelMapper;

    public void sendToUser(Notification notification) {
        notification.setTimestamp(new Date());
        notification = this.save(notification);
        simpMessagingTemplate.convertAndSendToUser(notification.getUser().getId().toString(), "/notify", notification);
    }

    public void sendToAll(Notification notification) {
        notification = this.save(notification);
        simpMessagingTemplate.convertAndSend("/all/notify", notification);
    }

    public List<NotificationDTO> getNotifications() {
        Long userId = authenticationService.getCurrentLoggedInUserId();
        return getNotifications(userId);
    }

    List<NotificationDTO> getNotifications(Long userId) {
        Pageable page = PageRequest.of(0, 2);
        List<Notification> notifications = notificationRepository.findByUserIdOrderByTimestampDesc(userId, page).getContent();
        List<NotificationDTO> notificationDTOs = new ArrayList<NotificationDTO>();
        for (Notification notification : notifications) 
            notificationDTOs.add(this.map(notification));
        return notificationDTOs;
    }

    protected NotificationDTO getNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        return this.map(notification);
    }

    protected Notification geNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow();
    }

    private NotificationDTO map(Notification notification) {
        TypeMap<Notification, NotificationDTO> propertyMapper = modelMapper.createTypeMap(Notification.class, NotificationDTO.class);
        propertyMapper.addMapping(src -> src.getUser().getId(), (dest, v) -> dest.setUserId((Long)v));
        NotificationDTO notificationDTO = propertyMapper.map(notification);
        return notificationDTO;
    }

    private Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }
    
}
