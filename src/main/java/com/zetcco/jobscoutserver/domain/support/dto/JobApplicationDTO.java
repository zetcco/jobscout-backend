package com.zetcco.jobscoutserver.domain.support.dto;

import com.zetcco.jobscoutserver.domain.support.ApplicationStatus;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobApplicationDTO {
    
    private Long id;
    private ApplicationStatus status;
    private ProfileDTO jobSeeker;
    private JobPostDTO jobPostDTO;

}
