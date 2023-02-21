package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillRepositoryTest {
    
    @Autowired
    private SkillRepository skillRepository;

    @Test
    public void saveSkill(){
        Skill skill  = Skill.builder()
                            .name("NodeJS")
                            .description("2 years or more axperience")
                            .build();
        skillRepository.save(skill);
    }

    @Test
    public void fetchSkillByName(){
        List<Skill> skills = skillRepository.findByNameIgnoreCase("NodejS");
        //skillRepository.find
        System.out.println(skills);
    }
}
