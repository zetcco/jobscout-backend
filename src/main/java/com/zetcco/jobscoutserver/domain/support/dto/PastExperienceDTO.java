package com.zetcco.jobscoutserver.domain.support.dto;

import com.zetcco.jobscoutserver.domain.support.PastExperience.JobTitle;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PastExperienceDTO {

    private Long id;
    private String startYear;
    private String endYear;
    
    private JobTitle jobTitle;
    
    private ProfileDTO organization;

}
