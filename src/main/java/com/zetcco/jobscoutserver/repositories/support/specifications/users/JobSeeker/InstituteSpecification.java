package com.zetcco.jobscoutserver.repositories.support.specifications.users.JobSeeker;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InstituteSpecification implements Specification<JobSeeker> {

    private List<Long> qualifications;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobSeeker> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (qualifications == null)
            return builder.isTrue(builder.literal(true));
        else {
            Join<User, Qualification> qualification = root.join("qualifications");
            Join<Qualification, Institute> institute = qualification.join("institute");
            return institute.get("id").in(qualifications);
        }
    }
    
}
