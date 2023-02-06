package com.zetcco.jobscoutdemo.controllers.auth;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/allopen")
    public ResponseEntity<String> allopen() {
        return ResponseEntity.ok("All open end");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            System.out.println(e);
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Bad Credentials").build(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Server Error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/organization")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody OrganizationRegisterRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.registerOrganization(request), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Company or Email already registered").build(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Server Error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register/jobseeker")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody JobSeekerRegistrationRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.registerJobSeeker(request), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Server Error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/register/jobcreator")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody JobCreatorRegistrationRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.registerJobCreator(request), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(AuthenticationResponse.builder().status("Server Error").build(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
