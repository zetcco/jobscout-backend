package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;
import com.zetcco.jobscoutserver.services.support.Qualification.QualificationService;

@Controller
@RequestMapping("/qualifications")
public class QualificationController {
    
    @Autowired
    private QualificationService qualificationService;

    @GetMapping("/degrees")
    public ResponseEntity<List<Degree>> getDegrees() {
        return new ResponseEntity<List<Degree>>(qualificationService.getDegrees(), HttpStatus.OK);
    }

    @GetMapping("/institutes")
    public ResponseEntity<List<Institute>> getInstitutes() {
        return new ResponseEntity<List<Institute>>(qualificationService.getInstitutes(), HttpStatus.OK);
    }
}
