package com.zetcco.jobscoutserver.repositories.support.specifications.JobPost;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TypeSpecification implements Specification<JobPost> {

    private List<JobPostType> types;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobPost> root, CriteriaQuery<?> criteria, CriteriaBuilder builder) {
        if (types == null)
            return builder.isTrue(builder.literal(true));
        else
            return root.get("type").in(types);
    }
    
}
