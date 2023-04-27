package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.Notification.NotificationDTO;
import com.zetcco.jobscoutserver.services.NotificationService;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping()
    public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestParam("limit") int pageSize, @RequestParam("offset") int pageCount) {
        try {
            return new ResponseEntity<List<NotificationDTO>>(notificationService.getNotificationsForCurrentUser(pageCount, pageSize), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<NotificationDTO> markAsSeen(@RequestBody Map<String, Long> request) {
        try {
            Long notificationId = request.get("id");
            return new ResponseEntity<NotificationDTO>(notificationService.markAsSeen(notificationId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
