package com.zetcco.jobscoutserver.services.support;

import java.io.Serializable;
import java.util.List;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.dto.CategorySkillSetDTO;
import com.zetcco.jobscoutserver.domain.support.dto.PastExperienceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CVDataRequest implements Serializable {
    
    Long id;
    Long templateId;
    String firstName;
    String lastName;
    String photo;
    String address;
    String contact;
    String email;
    String intro;

    List<Qualification> educationalQualifications;

    List<PastExperienceDTO> pastExperiences;

    List<CategorySkillSetDTO> categorySkillList;

}
