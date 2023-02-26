package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.zetcco.jobscoutserver.domain.RTCSignal;
import com.zetcco.jobscoutserver.services.RTCService;

@Controller
public class RTCController {
    
    @Autowired
    private RTCService callService;

    @MessageMapping("/call")
    public String recieveCallMessage(@Payload RTCSignal signal) {
        callService.sendToUser(signal);
        return "Success";
    }
}
