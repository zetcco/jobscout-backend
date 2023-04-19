package com.zetcco.jobscoutserver.repositories.questionary;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.questionary.Question;

@SpringBootTest
public class QuestionRepositoryTest {

    @Autowired
    private QuestionRepository questionRepository;

    @Test
    public void testCreateQuestion() {
        String question = "What out of this is not a compiled programming language?";
        List<String> answers = List.of("Typescript", "C", "Javascript", "Java");
        int answerIndex = 2;

        Question newQuestion = new Question(question, answers, answerIndex);
        questionRepository.save(newQuestion);
    }

    @Test
    public void getQuestion() {
        Question question = questionRepository.findById(1L).orElseThrow();
        System.out.println(question);
    }

    @Test
    public void checkAnswer() {
        Long questionId = 1L;
        int answerIndex = 1;

        System.out.println(questionRepository.checkIfAnswerIsCorrect(questionId, answerIndex));
    }

}
