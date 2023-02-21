package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.zetcco.jobscoutserver.domain.Skill;

public interface SkillRepository extends JpaRepository<Skill , Long>{

    List<Skill> findByNameIgnoreCase(String name);

}
