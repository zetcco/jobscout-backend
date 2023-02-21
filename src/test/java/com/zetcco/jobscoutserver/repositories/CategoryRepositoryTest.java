package com.zetcco.jobscoutserver.repositories;

import java.util.List;

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
    public void getAllCategories() throws Exception{
            List<Category> category =  categoryRepository.findAll();
            category.forEach((p)->{
                System.out.println(p.getName());
            }); 
    }

    @Test
    public void getCategoryByName(){
            List<Category> category = categoryRepository.findByNameIgnoreCase("Software Engineer");
            category.forEach((p)->{
                System.out.println(p.getName());
            });
    }        
    
}
