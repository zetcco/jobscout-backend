package com.zetcco.jobscoutdemo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutdemo.domain.JobCreator;
import com.zetcco.jobscoutdemo.domain.JobSeeker;
import com.zetcco.jobscoutdemo.domain.Organization;

@Controller
@RequestMapping("/protected")
public class DemoController {
    
    @GetMapping("/open")
    public ResponseEntity<String> openProtectedRoute() {
        return ResponseEntity.ok("Protected");
    }

    @GetMapping("/organization")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<String> organizationRoute() {
        return ResponseEntity.ok(((Organization)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getCompanyName());
    }

    @PostMapping("/organization/{id}")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<String> organizationProtectedRoute(@PathVariable Long id, @RequestBody String body) {
        if (((Organization)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == id)
            return new ResponseEntity<>("Authorized: " + body , HttpStatus.OK);
        else 
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/jobseeker")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> seekerRoute() {
        return ResponseEntity.ok(((JobSeeker)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFirstName());
    }

    @PostMapping("/jobseeker/{id}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<String> seekerProtectedRoute(@PathVariable Long id, @RequestBody String body) {
        if (((JobSeeker)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == id)
            return new ResponseEntity<>("Authorized: " + body , HttpStatus.OK);
        else 
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
    }

    @GetMapping("/jobcreator")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<String> creatorRoute() {
        return ResponseEntity.ok(((JobCreator)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getFirstName());
    }

    @PostMapping("/jobcreator/{id}")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<String> creatorProtectedRoute(@PathVariable Long id, @RequestBody String body) {
        if (((JobCreator)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId() == id)
            return new ResponseEntity<>("Authorized: " + body , HttpStatus.OK);
        else 
            return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
    }
}
