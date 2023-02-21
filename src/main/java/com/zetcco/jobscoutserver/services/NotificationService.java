package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.domain.support.NotificationStatus;
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

    private TypeMap<Notification, NotificationDTO> propertyMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.propertyMapper = modelMapper.createTypeMap(Notification.class, NotificationDTO.class);
    }

    public void sendToUser(Notification notification) {
        notification.setTimestamp(new Date());
        notification = this.save(notification);
        simpMessagingTemplate.convertAndSendToUser(notification.getUser().getId().toString(), "/notify", notification);
    }

    public void sendToAll(Notification notification) {
        notification = this.save(notification);
        simpMessagingTemplate.convertAndSend("/all/notify", notification);
    }

    public List<NotificationDTO> getNotificationsForCurrentUser(int pageCount, int pageSize) {
        Long userId = authenticationService.getCurrentLoggedInUserId();
        return getNotifications(userId, pageCount, pageSize);
    }

    public NotificationDTO markAsSeen(Long notificationId) throws NoSuchElementException, AccessDeniedException {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        if (notification.getUser().getId() != authenticationService.getCurrentLoggedInUserId())
            throw new AccessDeniedException("Notification does not belong to the specified user");
        notification.setStatus(NotificationStatus.READ);
        notification = this.save(notification);
        return this.mapNotification(notification);
    }

    List<NotificationDTO> getNotifications(Long userId, int pageCount, int pageSize) {
        Pageable page = PageRequest.of(pageCount, pageSize);
        List<Notification> notifications = notificationRepository.findByUserIdOrderByTimestampDesc(userId, page).getContent();
        return this.mapNotifications(notifications);
    }

    protected NotificationDTO getNotification(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow();
        return this.mapNotification(notification);
    }

    private NotificationDTO mapNotification(Notification notification) {
        propertyMapper.addMapping(src -> src.getUser().getId(), (dest, v) -> dest.setUserId((Long)v));
        NotificationDTO notificationDTO = propertyMapper.map(notification);
        return notificationDTO;
    }

    private List<NotificationDTO> mapNotifications(List<Notification> notifications) {
        return notifications
                .stream()
                .map( notification -> propertyMapper.map(notification))
                .collect(Collectors.toList());
    }

    private Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    Notification geNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow();
    }

    
}
