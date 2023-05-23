package com.zetcco.jobscoutserver.controllers.support;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaryAnswer {

    private Long questionId;
    private Integer answer;
    
}
