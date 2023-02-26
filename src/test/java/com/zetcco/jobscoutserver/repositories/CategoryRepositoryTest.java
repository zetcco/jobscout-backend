package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void addCategory(){
        Category category = Category.builder()
                                    .name("Software Engineering")
                                    .description("Senior Software Engineer")
                                    .build();
        categoryRepository.save(category);                              
    }

    
    @Test
    void testAddListOfCategories() {
        Category c1 = Category.builder().name("New category 1").description("Category 1 Desc").build();
        Category c2 = Category.builder().name("New category 2").description("Category 2 Desc").build();

        List<Category> categories = List.of(c1, c2);
        categoryRepository.saveAll(categories);
    }

    @Test
    public void getAllCategories(){
            List<Category> category = categoryRepository.findAll();
            category.forEach((p)->{
                System.out.println(p.getName());
            }); 
    }

    @Test
    public void getCategoryByName(){
            Optional<Category> category = categoryRepository.findByNameIgnoreCase("hr manager");
            category.ifPresent(p->{System.out.println(p.getName());});
            
    } 
    
    @Test
    public void getCategoryByIgnoringType(){
            List<Category> category = categoryRepository.findByNameContainingIgnoreCase("Software");
            category.forEach((p)->{
                System.out.println(p.getName());
            });
    }

    @Test
    public void getCategoryById(){
            Category category = categoryRepository.findById(2L).orElseThrow();
            System.out.println(category.getName());
    }
    
}
