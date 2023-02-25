package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryService categoryService;

    @Test
    void testAddNewCategory(){
        categoryService.addNewCategory("Service manager", "FullTime job with good salary");
    }
}
