package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.services.CategoryService;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("/search")
    public List<Category> getCategoryByContainingIgnoreType(@RequestParam("q") String name){
        return categoryService.getCategoryByNameContainingIgnoreCase(name);
    }

    @GetMapping("/{categoryId}")
    public Category getCategoryById(@PathVariable Long categoryId){
        return categoryService.getCategoryById(categoryId);
    }

    @PostMapping("/")
    public Category saveCategory(@RequestBody Category category){   
            return categoryService.addNewCategory(category);       
    }

    @PutMapping("/{categoryId}")
    public Category updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        return categoryService.updateCategory(categoryId, category);
    }

}
