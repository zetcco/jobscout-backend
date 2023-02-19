package com.zetcco.jobscoutserver.domain;

import java.time.OffsetDateTime;

import com.zetcco.jobscoutserver.domain.support.NotificationStatus;
import com.zetcco.jobscoutserver.domain.support.NotificationType;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private NotificationType type;
    private NotificationStatus status;
    private String header;
    private String content;
    
    private OffsetDateTime timestamp;

}
