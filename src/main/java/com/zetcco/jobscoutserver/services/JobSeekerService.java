package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.domain.support.dto.CategorySkillSetDTO;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.support.CategorySkillSetRepository;
import com.zetcco.jobscoutserver.repositories.support.PastExperience.JobTitleRepository;
import com.zetcco.jobscoutserver.services.mappers.CategorySkillSetMapper;
import com.zetcco.jobscoutserver.services.mappers.PastExperienceMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.JobSeeker.PastExperience.PastExperienceService;
import com.zetcco.jobscoutserver.services.support.JobSeeker.Qualification.QualificationService;

import jakarta.transaction.Transactional;

@Service
public class JobSeekerService {

    @Autowired
    private CategorySkillSetRepository categorySkillSetRepository;

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

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategorySkillSetMapper categorySkillSetMapper;

    @Autowired
    private JobTitleRepository jobTitleRepository;

    @Transactional
    public List<CategorySkillSetDTO> updateCategorySkillSet(List<CategorySkillSet> categorySkillSets) {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userService.getAuthUser().getId()).orElseThrow(() -> new NotFoundException("Job Seeker Not found"));
        for (CategorySkillSet categorySkillSet : categorySkillSets) {
            Category category = categoryService.getCategoryEntityById(categorySkillSet.getCategory().getId());
            categorySkillSet.setCategory(category);
            List<Skill> skills = categorySkillSet.getSkills();
            List<Skill> newSkills = new ArrayList<>();
            for (int index = 0; index < skills.size(); index++) {
                Skill skill = skills.get(index);
                if (skill.getId() == null) {
                    skill = skillService.addSkills(skill);
                    List<Skill> categorySkills = category.getSkills();
                    categorySkills.add(skill);
                    categoryService.updateCategory(category);
                }
                else skill = skillService.getSkillsById(skill.getId());
                newSkills.add(skill);
            }
            categorySkillSet.setSkills(newSkills);
        }
        List<CategorySkillSet> existingSet = jobSeeker.getCategorySkillSets();
        categorySkillSets = categorySkillSetRepository.saveAll(categorySkillSets);
        jobSeeker.setCategorySkillSets(categorySkillSets);
        jobSeeker = jobSeekerRepository.save(jobSeeker);
        if (existingSet != null)
            categorySkillSetRepository.deleteAll(existingSet);
        return categorySkillSetMapper.mapToDtos(categorySkillSets);
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

    public List<PastExperienceDTO> updateExperiences(List<PastExperience> pastExperiences) throws NotFoundException, DataIntegrityViolationException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(userService.getAuthUser().getId()).orElseThrow(() -> new NotFoundException("Job Seeker Not found"));
        for (PastExperience pastExperience : pastExperiences) 
            if (pastExperience.getJobTitle().getId() == null)
                pastExperience.setJobTitle(jobTitleRepository.save(pastExperience.getJobTitle()));
        pastExperiences = pastExperienceService.savePastExperiences(pastExperiences);
        jobSeeker.setPastExperiences(pastExperiences);
        jobSeeker = jobSeekerRepository.save(jobSeeker);
        return pastExperienceMapper.mapToDtos(jobSeeker.getPastExperiences());
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
    public List<CategorySkillSetDTO> getCategorySkillLists(Long id) throws NotFoundException {
        JobSeeker jobSeeker = this.getJobSeeker(id);
        List<CategorySkillSet> skillSet = jobSeeker.getCategorySkillSets();
        return categorySkillSetMapper.mapToDtos(skillSet);
    }

    @Transactional
    public String getIntro(Long id) throws NotFoundException {
        JobSeeker jobSeeker = this.getJobSeeker(id);
        return jobSeeker.getIntro();
    }
}
