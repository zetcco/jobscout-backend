package com.zetcco.jobscoutserver.services.questionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.questionary.Question;
import com.zetcco.jobscoutserver.repositories.questionary.QuestionRepository;

@Service
public class QuestionService {
    
    @Autowired
    private QuestionRepository questionRepository;

    public Question createQuestion(String question, List<String> answers, int answerIndex) {
        Question newQuestion = new Question(question, answers, answerIndex);
        return questionRepository.save(newQuestion);
    }

    public Question getQuestionById(Long id) {
        return questionRepository.findById(id).orElseThrow();
    }
    
    public List<Question> saveAll(List<Question> question) {
        return questionRepository.saveAll(question);
    }
}
