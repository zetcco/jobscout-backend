package com.zetcco.jobscoutserver.domain.support.dto;

import java.util.List;

import com.zetcco.jobscoutserver.domain.Skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategorySkillSetDTO {
    
    private CategoryDTO category;
    private List<Skill> skills;

}
