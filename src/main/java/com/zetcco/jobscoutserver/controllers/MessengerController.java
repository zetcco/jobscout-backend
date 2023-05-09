package com.zetcco.jobscoutserver.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Controller
public class MessengerController {

    @MessageMapping("/hello")
    public void greet(HelloMessage message) {
        System.out.println(message);
    }
    
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class HelloMessage {
    private String name;
}
