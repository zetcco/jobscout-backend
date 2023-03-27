package com.zetcco.jobscoutserver.repositories.support;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.CategorySkillSet;

public interface CategorySkillSetRepository extends JpaRepository<CategorySkillSet, Long> {
    
}
