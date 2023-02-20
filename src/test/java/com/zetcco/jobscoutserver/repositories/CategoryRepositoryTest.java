package com.zetcco.jobscoutserver.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Category;

@SpringBootTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void saveCategory(){
        Category category = Category.builder()
                                    .name("Software Engineer")
                                    .description("Senior Software Engineer")
                                    .build();
        categoryRepository.save(category);   
                                
    }
    
}
