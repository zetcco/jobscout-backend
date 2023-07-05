package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.repositories.support.UserBaseRepository;

public interface JobCreatorRepository extends UserBaseRepository<JobCreator>, JpaSpecificationExecutor<JobCreator> {
    
    Integer countByOrganizationId(Long orgId);
    List<JobSeeker> findByRecommendationRequestsId(Long id);
    
}
