package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillsRepositoryTest {

    @Autowired
    private SkillsRepository skillsRepository;

    @Test
    public void addSkills() {
        // Skill skill = new Skill("spring", "springjpa");
        Skill skill1 = Skill.builder()
                .name("react")
                .description("redux")
                .build();
        skillsRepository.save(skill1);
        // System.out.println(skill);

    }

    @Test
    public void fetchAllSkill() {
        List<Skill> skill = skillsRepository.findAll();
        System.out.println(skill);
    }

    // @Test
    // public void fetchSkillByName(String name) {
    // Skill skill = skillsRepository.findByName("spring");
    // System.out.println(skill);
    // }

}
