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
        categoryService.addNewCategory("Associate software engineer", "part time job with good salary");
    }

    @Test
    void testUpdateCategory(){
        List<Category> category1 = categoryService.getAllCategories();
        category1.forEach((p)->{
            System.out.println(p.getName());
        }); 
        categoryService.updateCategory("senior hr manager" , "HR Manager" , "full time with good salary");
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
        Category category = categoryService.getCategoryByNameIgnoreCase("HR manager");
        System.out.println(category.getName());
    }

    @Test
    void testGetCategoryByNameContainingIgnoreCase(){
        List<Category> category = categoryService.getCategoryByNameContainingIgnoreCase("engineer");
        category.forEach((p)->{
            System.out.println(p.getName());
        });
    }
}
