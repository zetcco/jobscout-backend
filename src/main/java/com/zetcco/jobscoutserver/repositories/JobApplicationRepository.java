package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zetcco.jobscoutserver.domain.JobApplication;

public interface JobApplicationRepository extends JpaRepository<JobApplication, Long>, JpaSpecificationExecutor<JobApplication> {
    
    List<JobApplication> findByJobSeekerId(Long id);
    JobApplication findByJobSeekerIdAndJobPostId(Long jobSeekerId, Long jobPostId);
    
}
