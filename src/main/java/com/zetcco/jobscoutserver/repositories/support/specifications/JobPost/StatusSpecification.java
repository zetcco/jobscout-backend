package com.zetcco.jobscoutserver.repositories.support.specifications.JobPost;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatusSpecification implements Specification<JobPost> {

    private JobPostStatus status;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobPost> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
        if (status == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.equal(root.get("status"), status);
    }
    
}
