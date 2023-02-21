package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Recommendation;


public interface RecommendationRepository extends JpaRepository<Recommendation, Long>{
    
}
