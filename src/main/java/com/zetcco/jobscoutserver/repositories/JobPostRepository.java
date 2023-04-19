package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.JobPost;
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    
}
