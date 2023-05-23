package com.zetcco.jobscoutserver.repositories.support.specifications.JobPost;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobPost;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobCreatorSpecification implements Specification<JobPost> {

    private Long jobCreatorId;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobPost> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (jobCreatorId == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.equal(root.get("jobCreator").get("id"), jobCreatorId);
    }
    
}
