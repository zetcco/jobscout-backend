package com.zetcco.jobscoutdemo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    
    @GetMapping("/protected")
    public void protectedRoute() {
        System.out.println("Protected route");
    }
}
