package com.zetcco.jobscoutserver.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

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

    public List<Skill> getCategoryAndSkillListById(Long categortId, List<Long> skillId) throws NotFoundException {
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
}