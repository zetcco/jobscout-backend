package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public void addNewCategory( String name , String description){
        Category category = this.categoryRepository.findByNameIgnoreCase(name);
        if( category == null){
            Category newCategory = new Category(null, name , description, null, null, null);
            categoryRepository.save(newCategory);
        }
    }

    public List<Category> getAllCategories() throws NotFoundException {
        List<Category> category = categoryRepository.findAll();
        if(category.isEmpty() == true){
            throw new NotFoundException("Categories Not Found!");
        }else{
            return category;
        }
        
    }

    public Category getCategoryByNameIgnoreCase(String name) throws NotFoundException{
            Category category = categoryRepository.findByNameIgnoreCase(name);
            if(category == null){
                throw new NotFoundException("Category Not Found!");
            }else{
                return category;
            }
    }

    public List<Category> getCategoryByNameContainingIgnoreCase(String name) throws NotFoundException{
            List<Category> category =  categoryRepository.findByNameContainingIgnoreCase(name);
            if(category.isEmpty() == true){
                throw new NotFoundException("Categories Not Found!");
            }else{
                return category;
            }
    }
}
