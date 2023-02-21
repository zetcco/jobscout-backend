package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillServiceTest {

    @Autowired
    private SkillService skillService;

    @Test
    void testAddSkills() throws DataIntegrityViolationException, NotFoundException {
        Skill skill = Skill.builder()
                .name("mongo")
                .description("mongoos")
                .build();
        skillService.addSkills(skill);
    }

    @Test
    void testSearchSkillByName() {
        System.out.println(skillService.searchSkillByName("react"));
    }
}
