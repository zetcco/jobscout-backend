package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.services.mappers.PastExperienceMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.JobSeeker.PastExperience.PastExperienceService;
import com.zetcco.jobscoutserver.services.support.JobSeeker.Qualification.QualificationService;

import jakarta.transaction.Transactional;

@Service
public class JobSeekerService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private QualificationService qualificationService;

    @Autowired
    private PastExperienceService pastExperienceService;

    @Autowired
    private PastExperienceMapper pastExperienceMapper;

    public List<Skill> updateCategoryAndSkillListById(Long categortId, List<Long> skillId) throws NotFoundException {
        Category categoryObj = categoryRepository.findById(categortId).orElseThrow();
        List<Skill> skillObj = new ArrayList<>();
        for (Long id : skillId) {
            skillObj.add(skillService.getSkillsById(id));
        }
        JobSeeker jobSeekerObj = jobSeekerRepository.findById(userService.getUser().getId()).orElseThrow();
        jobSeekerObj.setCategory(categoryObj);
        jobSeekerObj.setSkills(skillObj);
        jobSeekerRepository.save(jobSeekerObj);
        return skillObj;
    }

    public List<Qualification> updateQualifications(List<Qualification> qualifications) throws NotFoundException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userService.getAuthUser().getId()).orElseThrow(() -> new NotFoundException("Job Seeker Not found"));
        // for (Qualification qualification : qualifications) 
        //     qualification.setJobSeeker(jobSeeker);
        qualifications = qualificationService.saveQualifications(qualifications);
        jobSeeker.setQualifications(qualifications);
        jobSeeker = jobSeekerRepository.save(jobSeeker);
        return jobSeeker.getQualifications();
    }

    public List<PastExperience> updateExperiences(List<PastExperience> pastExperiences) throws NotFoundException, DataIntegrityViolationException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userService.getAuthUser().getId()).orElseThrow(() -> new NotFoundException("Job Seeker Not found"));
        pastExperiences = pastExperienceService.savePastExperiences(pastExperiences);
        jobSeeker.setPastExperiences(pastExperiences);
        jobSeeker = jobSeekerRepository.save(jobSeeker);
        return jobSeeker.getPastExperiences();
    }

    public String updateIntro(String intro) throws NotFoundException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userService.getAuthUser().getId()).orElseThrow(() -> new NotFoundException("Job Seeker Not found"));
        System.out.println(jobSeeker.getIntro());
        jobSeeker.setIntro(intro);
        jobSeeker = jobSeekerRepository.save(jobSeeker);
        return jobSeeker.getIntro();
    }

    public JobSeeker getJobSeeker(Long id) {
        return jobSeekerRepository.findById(id).orElseThrow(() -> new NotFoundException("Job Seeker with specified ID not found"));
    }

    @Transactional
    public List<Qualification> getQualificationsById(Long id) throws NotFoundException {
        JobSeeker jobSeeker = this.getJobSeeker(id);
        List<Qualification> qualifications = jobSeeker.getQualifications();
        return qualifications;
    }

    @Transactional
    public List<PastExperienceDTO> getPastExperience(Long id) throws NotFoundException {
        JobSeeker jobSeeker = this.getJobSeeker(id);
        List<PastExperience> pastExperiences = jobSeeker.getPastExperiences();
        return pastExperienceMapper.mapToDtos(pastExperiences);
    }

    @Transactional
    public String getIntro(Long id) throws NotFoundException {
        JobSeeker jobSeeker = this.getJobSeeker(id);
        return jobSeeker.getIntro();
    }
}
