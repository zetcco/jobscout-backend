package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    
}
