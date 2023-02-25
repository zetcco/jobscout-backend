package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    public void addNewCategory( String name , String description){
        List<Category> category = this.categoryRepository.findByNameIgnoreCase(name);
        if( category == null){
            Category newCategory = new Category(null, name , description, null, null, null);
            categoryRepository.save(newCategory);
        }
    }

}
