package com.zetcco.jobscoutserver.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
public interface JobPostRepository extends JpaRepository<JobPost, Long> {
    
    List<JobPost> findByTitleContainingIgnoreCase(String name);

    Optional<List<JobPost>> findByType(JobPostType type);

    Optional<List<JobPost>> findByStatus(JobPostStatus status);

}