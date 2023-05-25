package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.domain.support.dto.CategorySkillSetDTO;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;
import com.zetcco.jobscoutserver.repositories.support.specifications.users.JobSeeker.InstituteSpecification;
import com.zetcco.jobscoutserver.repositories.support.specifications.users.JobSeeker.NameSpecification;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;
import com.zetcco.jobscoutserver.services.support.StorageService;

@RestController
@RequestMapping("/job-seeker")
public class JobSeekerController {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private StorageService storageService;

    @PutMapping("/update/skills")
    public ResponseEntity<List<CategorySkillSetDTO>> updateJobSeekerSkillsAndCategory(@RequestBody List<CategorySkillSet> categorySkillSet) {
        try {
            return new ResponseEntity<List<CategorySkillSetDTO>>( jobSeekerService.updateCategorySkillSet(categorySkillSet), HttpStatus.OK);
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

    @GetMapping("{jobSeekerId}/intro")
    public ResponseEntity<String> getAbout(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<String>(jobSeekerService.getIntro(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/skillset")
    public ResponseEntity<List<CategorySkillSetDTO>> getSkillSet(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<List<CategorySkillSetDTO>>(jobSeekerService.getCategorySkillLists(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/recommendations")
    public ResponseEntity<List<RecommendationDTO>> getRecommendations(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<List<RecommendationDTO>>(jobSeekerService.gerRecommendations(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PutMapping("/update/qualifications")
    public ResponseEntity<List<Qualification>> updateQualifications(@RequestBody List<Qualification> qualifications) {
        try {
            return new ResponseEntity<List<Qualification>>(jobSeekerService.updateQualifications(qualifications), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/experiences")
    public ResponseEntity<List<PastExperienceDTO>> updateExperiences(@RequestBody List<PastExperience> experiences) {
        try {
            return new ResponseEntity<List<PastExperienceDTO>>(jobSeekerService.updateExperiences(experiences), HttpStatus.OK);
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

    @PutMapping("/update/intro-video")
    public ResponseEntity<String> setIntroVideo(@RequestParam("file") MultipartFile file) {
        try {
            String filename = storageService.store(file);
            return new ResponseEntity<String>(jobSeekerService.updateIntroVideo(filename), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("{jobSeekerId}/intro-video")
    public ResponseEntity<String> getIntroVideo(@PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<String>(jobSeekerService.getIntroVideo(jobSeekerId), HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfileDTO>> search(
        @RequestParam(required = false, value = "institutes") List<Long> institutes,
        @RequestParam(required = false, value = "name") String name
    ) {
        try {
            Specification<JobSeeker> specs = Specification.allOf(
                new InstituteSpecification(institutes),
                new NameSpecification(name)
            );
            return new ResponseEntity<List<ProfileDTO>>(jobSeekerService.searchForJobSeekers(specs), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{jobSeekerId}/stats")
    public ResponseEntity<List<Integer>> getStatus( @PathVariable Long jobSeekerId) {
        try {
            return new ResponseEntity<List<Integer>>(jobSeekerService.getJobSeekerStatus(jobSeekerId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
