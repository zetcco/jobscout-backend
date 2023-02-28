package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.dto.CategoryDTO;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testAddNewCategory(){
        CategoryDTO categoryDTO = CategoryDTO.builder()
                                                .name("UI/UX Engineer new")
                                                .description("Test Description")
                                                .build();
        System.out.println(categoryService.addNewCategory(categoryDTO));
    }

    @Test
    void testUpdateCategory(){
        CategoryDTO exisCategoryDTO = categoryService.getCategoryById(3L);
        exisCategoryDTO.setName("Updated Name 3");
        System.out.println(categoryService.updateCategory(exisCategoryDTO));
    }

    @Test
    void testGetAllCatgorie(){
        List<CategoryDTO> category = categoryService.getAllCategories();
        category.forEach((p)->{ System.out.println(p); }); 
    }

    @Test 
    void testGetCategoryByNameIgnoreCase(){
        System.out.println(categoryService.getCategoryByNameIgnoreCase("graphics designer").getName());
    }

    @Test
    void testGetCategoryByNameContainingIgnoreCase(){
        List<CategoryDTO> category = categoryService.getCategoryByNameContainingIgnoreCase("Software");
        category.forEach((p)->{ System.out.println(p); });
    }

    @Test
    void testGetById(){        
        System.out.print(categoryService.getCategoryById(2L).getName());
    }
}
