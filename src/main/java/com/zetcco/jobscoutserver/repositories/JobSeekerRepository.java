package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.repositories.support.UserBaseRepository;

public interface JobSeekerRepository extends UserBaseRepository<JobSeeker>, JpaSpecificationExecutor<JobSeeker> {
    
}
