package com.zetcco.jobscoutserver.services.questionary;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.QuestionaryForm;
import com.zetcco.jobscoutserver.domain.questionary.Question;
import com.zetcco.jobscoutserver.domain.questionary.Questionary;
import com.zetcco.jobscoutserver.repositories.questionary.QuestionaryRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

import jakarta.transaction.Transactional;

@Service
public class QuestionaryService {
    
    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Autowired
    private QuestionService questionService;

    public Questionary createQuestionary(QuestionaryForm data) {
        return this.createQuestionary(data.getName(), data.getBadge(), data.getDescription(), data.getTimePerQuestion(), data.getAttemptCount(), data.getQuestions());
    }

    public Questionary createQuestionary(String name, String badge, String description, Integer timePerQuestion, Integer attemptCount, List<Question> questions) {
        questions = questionService.saveAll(questions);
        Questionary questionary = new Questionary(null, name, badge, description, timePerQuestion, attemptCount, questions);
        return questionaryRepository.save(questionary);
    }

    @Transactional
    public float getMarks(Long questionaryId, List<Integer> answers) throws NotFoundException {

        Questionary questionary = this.getQuestionaryById(questionaryId);
        List<Question> questions = questionary.getQuestions();

        int marks = 0;
        for (int i = 0 ; i < answers.size() ; i++ )  
            if (questions.get(i).checkAnswer(answers.get(i)))
                marks++;

        return ((float)marks/questions.size())*100;

    }

    public Questionary getQuestionaryById(Long id) throws NotFoundException {
        return questionaryRepository.findById(id).orElseThrow(() -> new NotFoundException("Questionary not found"));
    }

    public List<Questionary> getQuestionaries() {
        return questionaryRepository.getAll();
    }

}
