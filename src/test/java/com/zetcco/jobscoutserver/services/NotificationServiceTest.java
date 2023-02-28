package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.domain.support.NotificationStatus;
import com.zetcco.jobscoutserver.domain.support.NotificationType;
import com.zetcco.jobscoutserver.repositories.UserRepository;
import com.zetcco.jobscoutserver.services.support.NotificationDTO;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    NotificationService notificationService;

    @Autowired
    UserRepository userRepository;

    @Test
    void testSendToAll() {
        Notification notification = Notification.builder()
                                                .type(NotificationType.JOIN_REQUEST)
                                                .status(NotificationStatus.UNREAD)
                                                .header("Test Message Header")
                                                .content("Test message content")
                                                .build();
        notificationService.sendToAll(notification);
    }

    @Test
    void testSendToUser() {
        Notification notification = Notification.builder()
                                                // .user(userRepository.findById(60L).orElseThrow())
                                                .type(NotificationType.JOIN_REQUEST)
                                                .status(NotificationStatus.UNREAD)
                                                .header("Test Message Header")
                                                .content("Test message content")
                                                .build();
        notificationService.sendToUser(notification);
    }

    @Test
    void testMapToDTO() {
        System.out.println( notificationService.getNotification(2L) );
    }

    @Test
    void testGetNotificationsForUser() {
        List<NotificationDTO> notificationDTOs = notificationService.getNotifications(95L, 0, 2);
        for (NotificationDTO notificationDTO : notificationDTOs) {
            System.out.println(notificationDTO);
        }
    }

    @Test
    void testGetNotificationById() {
        NotificationDTO notificationDTO = notificationService.getNotification(3L);
        System.out.println(notificationDTO);
        notificationDTO = notificationService.getNotification(4L);
        System.out.println(notificationDTO);
    }
}
