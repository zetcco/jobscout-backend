package com.zetcco.jobscoutserver.repositories.questionary;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zetcco.jobscoutserver.domain.questionary.Questionary;

public interface QuestionaryRepository extends JpaRepository<Questionary, Long> {

    @Query("SELECT new Questionary(q.id, q.name, q.badge, q.description, q.timePerQuestion, q.attemptCount) FROM Questionary q")
    List<Questionary> getAll();

    List<Questionary> findByNameContainingIgnoreCase(String q);
    
}
