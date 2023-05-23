package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationStatus;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationType;
import com.zetcco.jobscoutserver.repositories.UserRepository;
import com.zetcco.jobscoutserver.services.NotificationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
@RequestMapping("/test/notification")
public class DemoNotificationController {

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserRepository userRepository;
    
    @PostMapping("/all")
    public void sendToAll(@RequestBody NotificationSenderDTO notificationSenderDTO) {
        try {
            System.out.println(notificationSenderDTO);
            Notification notification = Notification.builder()
                                                    // .user(userRepository.findById(60L).orElseThrow())
                                                    .type(notificationSenderDTO.getType())
                                                    .status(notificationSenderDTO.getStatus())
                                                    .header(notificationSenderDTO.getHeader())
                                                    .content(notificationSenderDTO.getContent())
                                                    .build();
            notificationService.sendToAll(notification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/user/{id}")
    public void sendToAll(@PathVariable("id") Long id, @RequestBody NotificationSenderDTO notificationSenderDTO) {
        try {
            System.out.println(notificationSenderDTO);
            Notification notification = Notification.builder()
                                                    .user(userRepository.findById(id).orElseThrow())
                                                    .type(notificationSenderDTO.getType())
                                                    .status(notificationSenderDTO.getStatus())
                                                    .header(notificationSenderDTO.getHeader())
                                                    .content(notificationSenderDTO.getContent())
                                                    .build();
            notificationService.sendToUser(notification);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class NotificationSenderDTO {
    private NotificationType type;
    private NotificationStatus status;
    private String header;
    private String content;
}
