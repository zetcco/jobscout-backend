package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillsRepositoryTest {

    @Autowired
    private SkillsRepository skillsRepository;

    @Test
    public void addSkills() {
        Skill skill1 = Skill.builder()
                .name("react")
                .description("redux")
                .build();
        skillsRepository.save(skill1);
    }

    @Test
    public void fetchAllSkill() {
        List<Skill> skill = skillsRepository.findAll();
        System.out.println(skill);
    }

    @Test
    public void fetchSkillByName() {
        List<Skill> skill = skillsRepository.findByNameContainingIgnoreCase("spring");
        System.out.println(skill);
    }

}
