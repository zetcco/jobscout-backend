package com.zetcco.jobscoutserver.repositories.questionary;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttempt;

public interface QuestionaryAttemptRepository extends JpaRepository<QuestionaryAttempt, Long> {

    Optional<QuestionaryAttempt> findByJobSeekerIdAndQuestionaryId(Long jobSeekerId, Long questionaryId);
    List<QuestionaryAttempt> findByJobSeekerIdAndIsPublic(Long jobSeekerId, Boolean isPublic);
    List<QuestionaryAttempt> findByJobSeekerId(Long jobSeekerId);
    void deleteByQuestionaryId(Long questionaryId);
}
