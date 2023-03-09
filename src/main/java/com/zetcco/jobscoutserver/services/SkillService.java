package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.SkillsRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {

    @Autowired
    private SkillsRepository skillsRepository;

    // public List<Skill> searchSkillByName(String name, int pageCount, int
    // pageSize) {
    // if (name.equals(""))
    // throw new IllegalArgumentException("Wrong Parameters");
    // // Pageable page = PageRequest.of(pageCount, pageSize);
    // List<Skill> skills = skillsRepository.findByNameContainingIgnoreCase(name);
    // return skills;
    // }

    public List<Skill> searchSkillByName(String name) throws IllegalAccessException {
        if (name.equals(""))
            throw new IllegalArgumentException("Wrong Parameters");
        List<Skill> skills = skillsRepository.findByNameContainingIgnoreCase(name);
        return skills;
    }

    public List<Skill> fetchSkills() {
        List<Skill> skills = skillsRepository.findAll();
        return skills;
    }

    public Skill addSkills(Skill skill) throws DataIntegrityViolationException {
        return skillsRepository.save(skill);
    }

    public Skill getSkillsById(Long id) {
        return skillsRepository.findById(id).orElseThrow(() -> new NotFoundException("Skills Not Found"));
    }
}
