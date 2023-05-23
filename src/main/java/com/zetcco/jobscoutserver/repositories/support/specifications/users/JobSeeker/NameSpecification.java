package com.zetcco.jobscoutserver.repositories.support.specifications.users.JobSeeker;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobSeeker;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NameSpecification implements Specification<JobSeeker> {

    private String name;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobSeeker> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (name == null)
            return builder.isTrue(builder.literal(true));
        else {
            return builder.like(root.get("displayName"), "%"+name+"%");
        }
    }
    
}
