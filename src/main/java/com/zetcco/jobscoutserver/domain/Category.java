package com.zetcco.jobscoutserver.domain;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Skill> skills;

    @OneToMany(fetch = FetchType.LAZY)
    private List<JobSeeker> jobSeekers;

    @OneToMany(fetch = FetchType.LAZY)
    private List<JobPost> jobPosts;

    public int getSkillCount() {
        return this.skills.size();
    }

}
