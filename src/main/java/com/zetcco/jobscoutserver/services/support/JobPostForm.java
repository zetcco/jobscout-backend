package com.zetcco.jobscoutserver.services.support;

import com.zetcco.jobscoutserver.controllers.support.QuestionaryForm;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobPostForm {

    private JobPostDTO jobPost;
    private QuestionaryForm questionary;
    
}
