package com.zetcco.jobscoutserver.controllers.auth;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.auth.support.AuthenticationResponse;
import com.zetcco.jobscoutserver.controllers.auth.support.JobCreatorRegistrationRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.JobSeekerRegistrationRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.LoginRequest;
import com.zetcco.jobscoutserver.controllers.auth.support.OrganizationRegisterRequest;
import com.zetcco.jobscoutserver.services.auth.AuthenticationService;
import com.zetcco.jobscoutserver.services.support.StorageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private StorageService storageService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        try {
            return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
        } catch (DisabledException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Please activate your account before login");
        } catch (BadCredentialsException | InternalAuthenticationServiceException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Bad Credentials");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
        }
    }

    @PostMapping(path = "/register/organization", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<?> register(@RequestPart OrganizationRegisterRequest request, @RequestPart MultipartFile file) {
        try {
            String filename = storageService.store(file);
            request.setBrFileName(filename);
            authenticationService.registerOrganization(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Company Name or Email has been already registered");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
        }
    }

    @PostMapping("/register/jobseeker")
    public ResponseEntity<?> register(@RequestBody JobSeekerRegistrationRequest request) {
        try {
            authenticationService.registerJobSeeker(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
        }
    }
    
    @PostMapping("/register/jobcreator")
    public ResponseEntity<?> register(@RequestBody JobCreatorRegistrationRequest request) {
        try {
            authenticationService.registerJobCreator(request);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is already registered");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server Error");
        }
    }

    @PostMapping("/register/admin")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody Map<String, String> body) {
        try {
            return new ResponseEntity<AuthenticationResponse>(authenticationService.registerAdmin(body.get("email"), body.get("password")), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
