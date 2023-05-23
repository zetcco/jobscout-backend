package com.zetcco.jobscoutserver.repositories.support.specifications.JobApplication;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.zetcco.jobscoutserver.domain.JobApplication;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JobApplicationDegreeSpecification implements Specification<JobApplication> {

    private List<Long> degrees;

    @Override
    @Nullable
    public Predicate toPredicate(Root<JobApplication> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (degrees == null)
            return builder.isTrue(builder.literal(true));
        else {
            Join<JobApplication, JobSeeker> jobSeeker = root.join("jobSeeker");
            Join<JobSeeker, Qualification> qualification = jobSeeker.join("qualifications");
            Join<Qualification, Degree> degree = qualification.join("degree");
            return degree.get("id").in(degrees);
        }
    }

}
