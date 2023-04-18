package com.zetcco.jobscoutserver.services.mappers.questionary;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.questionary.Questionary;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttempt;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttemptDTO;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryDTO;
// import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.StorageService;

@Component
public class QuestionaryMapper {

    @Autowired
    private StorageService storageService;

    @Autowired
    private QuestionMapper questionMapper;

    // @Autowired
    // private UserMapper userMapper;

    public QuestionaryDTO mapQuestionaryToDto(Questionary questionary) {
        QuestionaryDTO questionaryDTO = new QuestionaryDTO(
            questionary.getId(),
            questionary.getName(),
            storageService.getResourceURL(questionary.getBadge()),
            questionary.getDescription(),
            questionary.getTimePerQuestion(),
            questionary.getAttemptCount(),
            questionary.getQuestions() != null ? questionary.getQuestions().stream().map(question -> questionMapper.mapToDto(question)).toList() : null
        );
        return questionaryDTO;
    }

    public List<QuestionaryDTO> mapQuestionariesToDTOs(List<Questionary> questionaries) {
        return questionaries.stream().map( questionary -> this.mapQuestionaryToDto(questionary) ).collect(Collectors.toList());
    }
    
    public QuestionaryAttemptDTO maptAttemptToDto(QuestionaryAttempt attempt) {
        attempt.getQuestionary().setQuestions(null);
        return new QuestionaryAttemptDTO(
            attempt.getId(),
            this.mapQuestionaryToDto(attempt.getQuestionary()), 
            // userMapper.mapToDto(attempt.getJobSeeker()), 
            attempt.getAttempts(), 
            attempt.getScore(), 
            attempt.getIsPublic()
        );
    }

    public List<QuestionaryAttemptDTO> mapAttemptsToDTOs(List<QuestionaryAttempt> attempts) {
        return attempts.stream().map( attempt -> this.maptAttemptToDto(attempt) ).collect(Collectors.toList());
    }
    
}
