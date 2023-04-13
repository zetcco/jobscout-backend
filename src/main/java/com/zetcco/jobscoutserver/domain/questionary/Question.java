package com.zetcco.jobscoutserver.domain.questionary;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answers;
    private int correctAnswer;

    public Question(String question, List<String> answers, int correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        setAnswers(answers);
    }

    public List<String> getAnswers() {
        return Arrays.asList(answers.split(","));
    }

    public void setAnswers(List<String> answers) {
        this.answers = String.join(",", answers);
    }

    public boolean checkAnswer(int answerIndex) {
        return this.correctAnswer == answerIndex;
    }
    
}
