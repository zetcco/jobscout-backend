package com.zetcco.jobscoutdemo.controllers.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

    @GetMapping("/auth/login")
    public void login() {
        System.out.println("Login request");
    }
    
}
