package com.zetcco.jobscoutserver.controllers.support;

import java.util.List;

import com.zetcco.jobscoutserver.domain.questionary.Question;

import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionaryForm {
    
    private String name;
    private String badge;
    private String description;
    private Integer timePerQuestion;
    private Integer attemptCount;

    @OneToMany
    private List<Question> questions;

}
