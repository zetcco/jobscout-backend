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
public class OrganizationSpecification implements Specification<JobPost> {

    private Long organization;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobPost> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (organization == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.equal(root.get("organization").get("id"), organization);
    }
    
}
