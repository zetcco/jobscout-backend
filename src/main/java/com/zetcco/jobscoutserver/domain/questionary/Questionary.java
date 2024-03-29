package com.zetcco.jobscoutserver.domain.questionary;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Questionary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String badge;
    private String description;
    private Integer timePerQuestion;
    private Integer attemptCount;

    @OneToMany
    private List<Question> questions;

    public Questionary(Long id, String name, String badge, String description, Integer timePerQuestion, Integer attemptCount) {
        this.id = id;
        this.name = name;
        this.badge = badge;
        this.description = description;
        this.timePerQuestion = timePerQuestion;
        this.attemptCount = attemptCount;
    }
    
    public Float getMarks(List<Integer> answers) {
        int marks = 0;
        for (int i = 0 ; i < answers.size() ; i++ )  
            if (questions.get(i).checkAnswer(answers.get(i)))
                marks++;

        return ((float)marks/questions.size())*100;
    }

    public List<Question> getRandomQuestionsFromPool(List<Integer> ids) {
        List<Question> questions = new ArrayList<>();
        for (Integer id : ids) 
            questions.add(this.getQuestions().get(id));
        return questions;
    }
    
}
