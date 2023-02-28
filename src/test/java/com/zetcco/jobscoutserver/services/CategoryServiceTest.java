package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testAddNewCategory(){
        Category category = Category.builder()
                                .name("Graphics Designer")
                                .description("part time job with good salary")
                                .build();
        categoryService.addNewCategory(category);
    }

    @Test
    void testUpdateCategory(){
        List<Category> category1 = categoryService.getAllCategories();
        category1.forEach((p)->{
            System.out.println(p.getName());
        }); 
        Category exisitingCategory = categoryService.getCategoryById(3L);
        exisitingCategory.setName("Updated");
        categoryService.updateCategory(exisitingCategory);
        List<Category> category2 = categoryService.getAllCategories();
        category2.forEach((p)->{
            System.out.println(p.getName());
        });
    }


    @Test
    void testGetAllCatgorie(){

        List<Category> category = categoryService.getAllCategories();
            category.forEach((p)->{
                System.out.println(p.getName());
            }); 
    }

    @Test 
    void testGetCategoryByNameIgnoreCase(){
        System.out.println(categoryService.getCategoryByNameIgnoreCase("graphics designer").getName());
    }

    @Test
    void testGetCategoryByNameContainingIgnoreCase(){
        List<Category> category = categoryService.getCategoryByNameContainingIgnoreCase("Software");
        category.forEach((p)->{
            System.out.println(p.getName());
        });
    }

    @Test
    void testGetById(){        
        System.out.print(categoryService.getCategoryById(2L).getName());
    }
}
