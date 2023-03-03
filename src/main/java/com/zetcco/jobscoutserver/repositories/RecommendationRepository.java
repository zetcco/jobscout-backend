package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zetcco.jobscoutserver.domain.Recommendation;

public interface RecommendationRepository extends JpaRepository <Recommendation, Long> {
    
}
