package com.zetcco.jobscoutserver.repositories.support.specifications.users.User;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CategorySpecification implements Specification<User> {

    private List<Long> categories;

    @Override
    @Nullable
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (categories == null)
            return builder.isTrue(builder.literal(true));
        else {
            Join<JobSeeker, CategorySkillSet> categorySkillSet = root.join("categorySkillSets");
            Join<CategorySkillSet, Category> category = categorySkillSet.join("category");
            return category.get("id").in(categories);
        }
    }
    
}
