package com.zetcco.jobscoutserver.domain.support.Notification;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long userId;
    private String image;
    private NotificationType type;
    private NotificationStatus status;
    private String header;
    private String content;
    private Date timestamp;
}

