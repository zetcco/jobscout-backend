package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Recommondation;

public interface RecommondationRepository extends JpaRepository<Recommondation, Long>{
    
}
