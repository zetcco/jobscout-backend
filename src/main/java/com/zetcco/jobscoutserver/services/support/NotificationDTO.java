package com.zetcco.jobscoutserver.services.support;

import java.util.Date;

import com.zetcco.jobscoutserver.domain.support.NotificationStatus;
import com.zetcco.jobscoutserver.domain.support.NotificationType;

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

