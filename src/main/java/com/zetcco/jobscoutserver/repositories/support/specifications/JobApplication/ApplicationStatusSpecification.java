package com.zetcco.jobscoutserver.repositories.support.specifications.JobApplication;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobApplication;
import com.zetcco.jobscoutserver.domain.support.ApplicationStatus;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ApplicationStatusSpecification implements Specification<JobApplication> {

    private ApplicationStatus status;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobApplication> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (status == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.equal(root.get("status"), status);
    }
}
