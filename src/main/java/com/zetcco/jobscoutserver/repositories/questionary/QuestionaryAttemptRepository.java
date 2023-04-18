package com.zetcco.jobscoutserver.repositories.questionary;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttempt;

public interface QuestionaryAttemptRepository extends JpaRepository<QuestionaryAttempt, Long> {

    QuestionaryAttempt findByJobSeekerIdAndQuestionaryId(Long jobSeekerId, Long questionaryId);
    
}
