package com.zetcco.jobscoutserver.controllers.support.PastExperience;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutserver.domain.support.PastExperience.JobTitle;
import com.zetcco.jobscoutserver.services.support.PastExperience.PastExperienceService;

@Controller
@RequestMapping("/experience")
public class PastExperienceController {
    
    @Autowired
    private PastExperienceService pastExperienceService;

    @GetMapping("/titles")
    public ResponseEntity<List<JobTitle>> getAllJobTitles() {
        return new ResponseEntity<List<JobTitle>>(pastExperienceService.getAllJobTitles(), HttpStatus.OK);
    }

}
