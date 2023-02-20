package com.zetcco.jobscoutserver.repositories;

import com.zetcco.jobscoutserver.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillsRepository extends JpaRepository<Skill, Long> {
    Skill findByName(String name);
}
