package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.Skill;

@SpringBootTest
public class SkillsRepositoryTest {

    @Autowired
    private SkillsRepository skillsRepository;

    @Autowired
    private CategoryRepository categoryRepository;

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

    @Test
    public void addSkillToCategory() {
        Skill skill1 = Skill.builder()
                .name(".NET")
                .description("Development with .NET framework")
                .build();
        skill1 = skillsRepository.save(skill1);
        Category category = categoryRepository.findById(8L).orElseThrow();
        List<Skill> skills = category.getSkills();
        skills.add(skill1);
        categoryRepository.save(category);
    }

}
