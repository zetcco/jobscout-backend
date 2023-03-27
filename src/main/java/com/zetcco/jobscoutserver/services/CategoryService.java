package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.dto.CategoryDTO;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.services.mappers.CategoryMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class CategoryService {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper mapper;

    public CategoryDTO addNewCategory(CategoryDTO categoryDTO) throws DataIntegrityViolationException {
        if (categoryDTO.getId() == null && categoryRepository.findByNameContainingIgnoreCase(categoryDTO.getName()).isEmpty()) {
            Category category = mapper.mapToEntity(categoryDTO);
            return this.mapper.mapToDto(categoryRepository.save(category));
        }
        else
            throw new DataIntegrityViolationException("Category already exists!");
    }

    public CategoryDTO updateCategory(CategoryDTO categoryDTO) throws NotFoundException {
        Category exsistingCategory = mapper.mapToEntity(categoryDTO);
        if (!categoryRepository.existsById(exsistingCategory.getId()))
            throw new NotFoundException("Category not found!");
        return this.mapper.mapToDto(categoryRepository.save(exsistingCategory));
    }

    public List<CategoryDTO> getAllCategories() {
        return this.mapper.mapToDtos(categoryRepository.findAll());   
    }

    public CategoryDTO getCategoryByNameIgnoreCase(String name) throws NotFoundException{
           Category category = categoryRepository.findByNameIgnoreCase(name)
                .orElseThrow(()->new NotFoundException("Category Not Found!"));

            return this.mapper.mapToDto(category);
    }

    public List<CategoryDTO> getCategoryByNameContainingIgnoreCase(String name) throws NotFoundException{
            return this.mapper.mapToDtos(categoryRepository.findByNameContainingIgnoreCase(name));
    }

    public Category getCategoryEntityById(Long Id) throws NotFoundException {
            Category category = categoryRepository.findById(Id).orElseThrow(() -> new NotFoundException("Category Not Found!"));        
            return category;
    }

    public CategoryDTO getCategoryById(Long Id) throws NotFoundException {
            Category category = categoryRepository.findById(Id)
                .orElseThrow(() -> new NotFoundException("Category Not Found!"));        
            return this.mapper.mapToDto(category);
    }

    public List<Skill> getSkillsByCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category Not Found"));
        return category.getSkills();
    }
}
