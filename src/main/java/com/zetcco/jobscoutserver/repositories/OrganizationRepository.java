package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.support.UserBaseRepository;

public interface OrganizationRepository extends UserBaseRepository<Organization> {

    @Query(value = """
            select *
            from organization o1_0 join _user o1_1 on o1_0.id=o1_1.id where 
            to_tsvector(o1_0.company_name) @@ to_tsquery(:keyword)""",
            nativeQuery = true)
    public Page<Organization> findOrganizationByNameFTS(@Param("keyword") String keyword, Pageable page);

    Page<Organization> findByCompanyNameContainingIgnoreCase(String name, Pageable page);
}
