package com.zetcco.jobscoutserver.controllers;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.UserService;

@RestController
@RequestMapping("/job-seeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private UserService userService;

    @PutMapping("/update/skills/{categoryId}")
    public ResponseEntity<List<Skill>> updateJobSeekerSkillsAndCategory(@PathVariable Long categoryId,
            @RequestBody Map<String, List<Long>> request) {

        try {
            List<Long> participantIds = request.get("ids");
            return new ResponseEntity<List<Skill>>(
                    jobSeekerService.updateCategoryAndSkillListById(categoryId, participantIds),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/generate-cv/{templateId}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Resource> generateCv(@PathVariable Long templateId) {
        Long requesterId = userService.getUser().getId();
        try {
            Resource cvPdf = jobSeekerService.generateCV(requesterId, templateId);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Disposition", String.format("cv-%d-%d.pdf", requesterId, templateId));
            responseHeaders.set("Content-Type", "application/pdf");
            return ResponseEntity.ok().headers(responseHeaders).body(cvPdf);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
