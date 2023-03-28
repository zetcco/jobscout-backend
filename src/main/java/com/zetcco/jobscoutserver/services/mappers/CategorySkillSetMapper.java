package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;
import com.zetcco.jobscoutserver.domain.support.dto.CategorySkillSetDTO;

@Component
public class CategorySkillSetMapper {

    @Autowired
    private CategoryMapper categoryMapper;
    
    public CategorySkillSetDTO mapToDto(CategorySkillSet categorySkillSet) {
        return new CategorySkillSetDTO(
            categoryMapper.mapToDto(categorySkillSet.getCategory()),
            categorySkillSet.getSkills()
        );
    }

    public List<CategorySkillSetDTO> mapToDtos(List<CategorySkillSet> categorySkillSets) {
        return categorySkillSets.stream().map( categorySkillSet -> this.mapToDto(categorySkillSet)).collect(Collectors.toList());
    } 

}
