package com.zetcco.jobscoutserver.domain;

import java.util.Date;

import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationStatus;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationType;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private NotificationType type;
    private NotificationStatus status;
    private String header;
    private String content;
    
    private Date timestamp;

    public Notification(User user, String header, String content, NotificationType type) {
        this.user = user;
        this.type = type;
        this.status = NotificationStatus.UNREAD;
        this.header = header;
        this.content = content;
        this.timestamp = new Date();
    }

}
