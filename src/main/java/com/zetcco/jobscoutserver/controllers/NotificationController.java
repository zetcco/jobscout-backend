package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutserver.services.NotificationService;
import com.zetcco.jobscoutserver.services.support.NotificationDTO;

@Controller
@RequestMapping("/notification")
public class NotificationController {
    
    @Autowired
    private NotificationService notificationService;

    // @GetMapping("/{id}")
    // public ResponseEntity<List<NotificationDTO>> getNotifications(@PathVariable("id") Long id) {
    //     try {
    //         // notificationService.get
    //     } catch (Exception e) {

    //     }
    // }

}
