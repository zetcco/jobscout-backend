package com.zetcco.jobscoutserver.domain.questionary;

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
    
}
