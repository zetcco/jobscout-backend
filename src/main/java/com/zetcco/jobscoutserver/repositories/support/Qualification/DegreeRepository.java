package com.zetcco.jobscoutserver.repositories.support.Qualification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;

public interface DegreeRepository extends JpaRepository<Degree, Long> {
    
}
