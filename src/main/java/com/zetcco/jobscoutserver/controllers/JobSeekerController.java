package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.services.JobSeekerService;

@RestController
@RequestMapping("/")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @GetMapping("/job-seeker")
    public ResponseEntity<JobSeeker> getJobSeeker(@PathVariable Long categoryId, @PathVariable List<Long> skillId) {

        try {
            return new ResponseEntity<JobSeeker>(jobSeekerService.getCategoryAndSkillListById(categoryId, skillId),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
