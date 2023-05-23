package com.zetcco.jobscoutserver.repositories.support.specifications.JobApplication;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobApplication;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobApplicationUrgentSpecification implements Specification<JobApplication> {

    private Boolean urgent;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobApplication> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (urgent == null)
            return builder.isTrue(builder.literal(true));
        else {
            return builder.equal(root.get("urgent"), urgent);
        }
    }

}
