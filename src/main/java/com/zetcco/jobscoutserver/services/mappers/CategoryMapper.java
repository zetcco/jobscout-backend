package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.support.dto.CategoryDTO;

@Component
public class CategoryMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO mapToDto(Category category) {
        return this.modelMapper.map(category, CategoryDTO.class);
    }

    public List<CategoryDTO> mapToDtos(List<Category> categories) {
        return categories.stream().map(category -> mapToDto(category)).toList();
    }

    public Category mapToEntity(CategoryDTO categoryDTO) {
        return this.modelMapper.map(categoryDTO, Category.class);
    }

    public List<Category> mapToEntities(List<CategoryDTO> dtos) {
        return dtos.stream().map(dto -> mapToEntity(dto)).toList();
    }

}
