package com.zetcco.jobscoutserver.repositories.questionary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.zetcco.jobscoutserver.domain.questionary.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query("SELECT CASE WHEN (q.correctAnswer = :answerIndex) THEN true ELSE false END FROM Question q WHERE q.id = :questionId")
    boolean checkIfAnswerIsCorrect(Long questionId, int answerIndex);
    
}
