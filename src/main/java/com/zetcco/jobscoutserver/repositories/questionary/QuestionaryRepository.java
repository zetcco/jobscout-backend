package com.zetcco.jobscoutserver.repositories.questionary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.questionary.Questionary;

public interface QuestionaryRepository extends JpaRepository<Questionary, Long> {
    
}
