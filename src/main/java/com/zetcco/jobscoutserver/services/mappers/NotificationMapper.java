package com.zetcco.jobscoutserver.services.mappers;

import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationDTO;

@Component
public class NotificationMapper {
    
    public NotificationDTO mapToDto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO(notification.getId(),
            notification.getUser().getId(),
            null, 
            notification.getType(),
            notification.getStatus(), 
            notification.getHeader(),
            notification.getContent(),
            notification.getTimestamp());
        return notificationDTO;
    }

}
