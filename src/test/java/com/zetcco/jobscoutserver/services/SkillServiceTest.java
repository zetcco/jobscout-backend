package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillServiceTest {

    @Autowired
    private SkillService skillService;

    @Test
    void testAddSkills() {
        Skill skill = Skill.builder()
                .name("mongo")
                .description("mongoos")
                .build();
        skillService.addSkills(skill);
    }

    @Test
    void testSearchSkillByName() {
        try {
            System.out.println(skillService.searchSkillByName("react"));
        } catch (IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void testFetchSkills() {
        System.out.println(skillService.fetchSkills());
    }
}
