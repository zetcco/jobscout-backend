package com.zetcco.jobscoutserver.services.support.JobSeeker.Qualification;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.repositories.support.Qualification.DegreeRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.InstituteRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.QualificationRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class QualificationService {

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private QualificationRepository qualificationRepository;   

    public Qualification saveQualification(Qualification qualification) {
        return qualificationRepository.save(qualification);
    }

    public List<Qualification> saveQualifications(List<Qualification> qualifications) {
        return qualificationRepository.saveAll(qualifications);
    }

    public Degree getDegreeById(Long id) {
        return degreeRepository.findById(id).orElseThrow(() -> new NotFoundException("Degree not found"));
    }

    public Institute getInstitute(Long id) {
        return instituteRepository.findById(id).orElseThrow(() -> new NotFoundException("Institute not found"));
    }

    public List<Degree> getDegrees() {
        return degreeRepository.findAll();
    }

    public List<Institute> getInstitutes() {
        return instituteRepository.findAll();
    }

}
