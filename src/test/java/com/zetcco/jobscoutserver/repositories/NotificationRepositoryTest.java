package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.zetcco.jobscoutserver.domain.Notification;

@SpringBootTest
public class NotificationRepositoryTest {

    @Autowired
    private NotificationRepository notificationRepository;

    @Test
    void testFindByIdOrderByTimeStampDesc() {
        Pageable page = PageRequest.of(0, 2);
        List<Notification> notifications = notificationRepository.findByUserIdOrderByTimestampDesc(95L, page).getContent();
        for (Notification notification : notifications) {
            System.out.println("\n----------------------------------------------\n");
            System.out.println(notification);
            System.out.println("\n----------------------------------------------\n");
        }
    }
}
