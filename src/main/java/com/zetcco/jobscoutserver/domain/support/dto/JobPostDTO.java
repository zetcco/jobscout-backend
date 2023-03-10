package com.zetcco.jobscoutserver.domain.support.dto;

import java.util.Date;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.zetcco.jobscoutserver.domain.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Data;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@JsonInclude(Include.NON_NULL)
public class JobPostDTO {

    private Long Id;
    private Date timestamp;
    private Date dueDate;
    private String title;
    private String description;
    private JobPostType type;
    private Boolean urgent;
    private JobPostStatus status;
    private Category category;
    private ProfileDTO jobCreator;
    private ProfileDTO organization;
    
}
