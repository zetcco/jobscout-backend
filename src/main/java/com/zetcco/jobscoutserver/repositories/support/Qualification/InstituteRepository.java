package com.zetcco.jobscoutserver.repositories.support.Qualification;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;

public interface InstituteRepository extends JpaRepository<Institute, Long> {
    
}
