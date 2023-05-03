package com.zetcco.jobscoutserver.repositories.support.specifications.users.User;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NameSpecification implements Specification<User> {

    private String name;

    @Override
    @Nullable
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (name == null)
            return builder.isTrue(builder.literal(true));
        else
            return builder.like(root.get("displayName"), "%"+name+"%");
    }
    
}
