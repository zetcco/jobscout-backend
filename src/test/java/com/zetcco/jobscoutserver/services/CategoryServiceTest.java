package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.dto.CategoryDTO;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.SkillsRepository;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillsRepository skillsRepository;

    @Test
    void testAddNewCategory() {
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .name("UI/UX Engineer new")
                .description("Test Description")
                .build();
        System.out.println(categoryService.addNewCategory(categoryDTO));
    }

    @Test
    void testAddNewCategory_skills() {

        Skill skill1 = Skill.builder()
                .name("Angular")
                .description("Angular road map")
                .build();
        Skill skill2 = Skill.builder()
                .name("ract")
                .description("ract road map")
                .build();
        Skill skill3 = Skill.builder()
                .name("Laraval")
                .description("php road map")
                .build();

        List<Skill> skills = List.of(skill1, skill2, skill3);
        skillsRepository.saveAll(skills);

        Category category = Category.builder()
                .name("It & Technology")
                .description("Software Field")
                .skills(skills)
                .build();

        System.out.println(categoryRepository.save(category));
    }

    @Test
    void testUpdateCategory() {
        CategoryDTO exisCategoryDTO = categoryService.getCategoryById(3L);
        exisCategoryDTO.setName("Updated Name 3");
        System.out.println(categoryService.updateCategory(exisCategoryDTO));
    }

    @Test
    void testGetAllCatgorie() {
        List<CategoryDTO> category = categoryService.getAllCategories();
        category.forEach((p) -> {
            System.out.println(p);
        });
    }

    @Test
    void testGetCategoryByNameIgnoreCase() {
        System.out.println(categoryService.getCategoryByNameIgnoreCase("graphics designer").getName());
    }

    @Test
    void testGetCategoryByNameContainingIgnoreCase() {
        List<CategoryDTO> category = categoryService.getCategoryByNameContainingIgnoreCase("Software");
        category.forEach((p) -> {
            System.out.println(p);
        });
    }

    @Test
    void testGetById() {
        System.out.print(categoryService.getCategoryById(2L).getName());
    }

    @Test
    void testGetskillsByCategory() {
        Category category = categoryRepository.findById(2L).orElseThrow();
        System.out.println(category.getSkills());
    }

    @Test
    void testGetCategoryByIdCategory() {
        Category category = categoryRepository.findById(2L).orElseThrow();
        System.out.println(category);
    }
}
