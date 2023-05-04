package com.zetcco.jobscoutserver.services.mappers.questionary;

import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.questionary.Question;
import com.zetcco.jobscoutserver.domain.questionary.QuestionDTO;

@Component
public class QuestionMapper {
    
    public QuestionDTO mapToDto(Question question) {
        return this.mapToDto(question, false);
    }
    
    public QuestionDTO mapToDto(Question question, Boolean showAnswers) {
        return new QuestionDTO(question.getId(), question.getQuestion(), question.getAnswers(), showAnswers ? question.getCorrectAnswer() : null);
    }
    
}
