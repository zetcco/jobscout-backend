package com.zetcco.jobscoutdemo.controllers.auth;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutdemo.controllers.auth.support.AuthenticationResponse;
import com.zetcco.jobscoutdemo.controllers.auth.support.JobCreatorRegistrationRequest;
import com.zetcco.jobscoutdemo.controllers.auth.support.JobSeekerRegistrationRequest;
import com.zetcco.jobscoutdemo.controllers.auth.support.LoginRequest;
import com.zetcco.jobscoutdemo.controllers.auth.support.OrganizationRegisterRequest;
import com.zetcco.jobscoutdemo.services.auth.AuthenticationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register/organization")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody OrganizationRegisterRequest request) {
        return ResponseEntity.ok(authenticationService.registerOrganization(request));
    }

    @PostMapping("/register/jobseeker")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody JobSeekerRegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registerJobSeeker(request));
    }
    
    @PostMapping("/register/jobcreator")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody JobCreatorRegistrationRequest request) {
        return ResponseEntity.ok(authenticationService.registerJobCreator(request));
    }
}
