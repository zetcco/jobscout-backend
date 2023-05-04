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
public class DescriptionSpecification implements Specification<JobPost> {

    private String query;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobPost> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
        if (query == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.equal(builder.function("fts", Boolean.class, root.get("description"), builder.literal(query)), builder.literal(true));
    }
    
}
