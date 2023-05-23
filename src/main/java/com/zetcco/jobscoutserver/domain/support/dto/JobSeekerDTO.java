package com.zetcco.jobscoutserver.domain.support.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.domain.support.Gender;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.domain.support.PastExperience.PastExperience;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class JobSeekerDTO{
    
    private ProfileDTO profile;
    private Gender gender;
    private String intro;
    private CategoryDTO category;
    private List<Qualification> qualifications;
    private List<PastExperience> pastExperiences;
    private List<Recommendation> recommendations;

}
