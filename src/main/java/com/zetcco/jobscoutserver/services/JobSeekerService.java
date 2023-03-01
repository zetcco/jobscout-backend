package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.SkillsRepository;

@Service
public class JobSeekerService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    public List<Skill> fetchSkillsByCategory() {
        return null;
    }
    
}
