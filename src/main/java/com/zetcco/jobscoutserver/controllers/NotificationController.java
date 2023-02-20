package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.NotificationService;
import com.zetcco.jobscoutserver.services.support.NotificationDTO;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    @GetMapping()
    public ResponseEntity<List<NotificationDTO>> getNotifications(@RequestParam("limit") int pageSize, @RequestParam("offset") int pageCount) {
        try {
            return new ResponseEntity<List<NotificationDTO>>(notificationService.getNotificationsForCurrentUser(pageCount, pageSize), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
