package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.SkillsRepository;

@Service
public class JobSeekerService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private SkillService skillService;
    
    public JobSeeker getCategoryAndSkillListById(Long categortId, List<Long> skillId) {
        Optional<Category> categoryObj = categoryRepository.findById(categortId);
        List<Skill> skillObj = new ArrayList<>();
        for (Long id : skillId) {
            // skillObj.add(skillsRepository.findById(id));
            skillObj.add(skillService.getSkillsById(id));
        }
        JobSeeker jobSeekerObj = new JobSeeker();
        jobSeekerObj.setCategory(categoryObj);
        jobSeekerObj.setSkills(skillObj);
        return JobSeekerRepository.save(jobSeekerObj);
    }
}
