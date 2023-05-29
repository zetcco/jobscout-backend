package com.zetcco.jobscoutserver.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.support.VerificationService;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    @Autowired
    private VerificationService verificationService;

    @GetMapping
    public ResponseEntity<String> verifyAccount(@RequestParam UUID key) {
        try {
            verificationService.verifyAccount(key);
            return new ResponseEntity<String>("Your account is now verified. You can now Log-in", HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
