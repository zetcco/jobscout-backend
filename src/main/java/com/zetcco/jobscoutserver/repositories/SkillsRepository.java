package com.zetcco.jobscoutserver.repositories;

import com.zetcco.jobscoutserver.domain.Skill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillsRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByNameContainingIgnoreCase(String name);
}
