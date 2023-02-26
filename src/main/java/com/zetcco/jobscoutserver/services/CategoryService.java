package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public Category addNewCategory(Category category){
        return categoryRepository.findByNameIgnoreCase(category.getName())
        .orElse(categoryRepository.save(category));
    }

    public Category updateCategory(Long categoryId, Category category){
        Category exsistingCategory = categoryRepository.findById(categoryId)
        .orElseThrow(()->new NotFoundException("Such A Category Not Found!"));
            exsistingCategory.setName(category.getName());
            exsistingCategory.setDescription(category.getDescription());
            categoryRepository.save(exsistingCategory);
            return exsistingCategory;
    }

    public List<Category> getAllCategories() throws NotFoundException {
            return categoryRepository.findAll();   
    }

    public Category getCategoryByNameIgnoreCase(String name) throws NotFoundException{
           return categoryRepository.findByNameIgnoreCase(name)
           .orElseThrow(()->new NotFoundException("Category Not Found!"));
    }

    public List<Category> getCategoryByNameContainingIgnoreCase(String name) throws NotFoundException{
            return categoryRepository.findByNameContainingIgnoreCase(name);
    }

    public Category getCategoryById(Long Id) throws NotFoundException{
            return  categoryRepository.findById(Id)
            .orElseThrow(() -> new NotFoundException("Category Not Found!"));        
    }
}
