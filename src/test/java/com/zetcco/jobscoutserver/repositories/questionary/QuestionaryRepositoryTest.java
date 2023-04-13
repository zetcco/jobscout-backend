package com.zetcco.jobscoutserver.repositories.questionary;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.questionary.Question;
import com.zetcco.jobscoutserver.domain.questionary.Questionary;

import jakarta.transaction.Transactional;

@SpringBootTest
public class QuestionaryRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private QuestionaryRepository questionaryRepository;

    @Test
    public void testCreateQuestionary() {

        Question q1 = questionRepository.findById(1L).orElseThrow();
        Question q2 = questionRepository.findById(2L).orElseThrow();

        Questionary questionary = new Questionary(null, "Basic Programming", null, null, List.of(q1, q2));

        questionary = questionaryRepository.save(questionary);

        System.out.println(questionary);

    }

    @Transactional
    @Test
    public void checkAnswers() {
        List<Integer> answers = List.of(3,1);
        Questionary questionary = questionaryRepository.findById(1L).orElseThrow();

        List<Question> questions = questionary.getQuestions();

        int marks = 0;
        for (int i = 0 ; i < answers.size() ; i++ )  
            if (questions.get(i).checkAnswer(answers.get(i)))
                marks++;
        
        System.out.println(marks);
        System.out.println(questions.size());
        System.out.println(((float)marks/questions.size())*100);
        
    }

}
