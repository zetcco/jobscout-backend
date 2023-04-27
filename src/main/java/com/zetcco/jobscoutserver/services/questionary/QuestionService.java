package com.zetcco.jobscoutserver.services.questionary;

import java.util.ArrayList;
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

    public void deleteAll(List<Question> questions) {
        questionRepository.deleteAll(questions);
    }

    public void deleteAllById(List<Long> questions) {
        questionRepository.deleteAllById(questions);
    }

    public List<Question> updateQuestions(List<Question> questions) {
        List<Question> updatedQuestions = new ArrayList<>();
        for (Question question : questions) {
            Question newQuestion;
            if (question.getId() != null) {
                newQuestion = this.getQuestionById(question.getId());
                newQuestion.setAnswers(question.getAnswers());
                newQuestion.setCorrectAnswer(question.getCorrectAnswer());
                newQuestion.setQuestion(question.getQuestion());
            } else 
                newQuestion = new Question(question.getQuestion(), question.getAnswers(), question.getCorrectAnswer());

            updatedQuestions.add( questionRepository.save(newQuestion) );
        }
        return updatedQuestions;
    }
}
