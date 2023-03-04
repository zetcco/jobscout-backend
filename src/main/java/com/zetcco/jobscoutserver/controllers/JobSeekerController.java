package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.services.JobSeekerService;

@RestController
@RequestMapping("/job-seeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @PutMapping("/update/skills/{categoryId}")
    public ResponseEntity<List<Skill>> updateJobSeekerSkillsAndCategory(@PathVariable Long categoryId,
            @RequestBody Map<String, List<Long>> request) {

        try {
            List<Long> participantIds = request.get("ids");
            return new ResponseEntity<List<Skill>>(
                    jobSeekerService.getCategoryAndSkillListById(categoryId, participantIds),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
