package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.JobSeeker.JobSeekerAbout;

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
                    jobSeekerService.updateCategoryAndSkillListById(categoryId, participantIds),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/qualifications")
    public ResponseEntity<List<Qualification>> getQualifications(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<List<Qualification>>(jobSeekerService.getQualificationsById(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/experiences")
    public ResponseEntity<List<PastExperienceDTO>> getPastExperiences(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<List<PastExperienceDTO>>(jobSeekerService.getPastExperience(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/about")
    public ResponseEntity<JobSeekerAbout> getAbout(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<JobSeekerAbout>(jobSeekerService.getAbout(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update/qualifications")
    public void updateQualifications(@RequestBody List<Qualification> qualifications) {
        try {
            jobSeekerService.updateQualifications(qualifications);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/experiences")
    public void updateExperiences(@RequestBody List<PastExperience> experiences) {
        try {
            jobSeekerService.updateExperiences(experiences);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid input parameters");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/intro")
    public ResponseEntity<String> updateIntro(@RequestBody String intro) {
        try {
            return new ResponseEntity<>(jobSeekerService.updateIntro(intro), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            System.out.println(e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
