package com.zetcco.jobscoutserver.repositories.support.PastExperience;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.PastExperience.JobTitle;

public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {
    
}
