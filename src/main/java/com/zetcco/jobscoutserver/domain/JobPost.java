package com.zetcco.jobscoutserver.domain;

import java.util.Date;
import java.util.List;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobPost {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dueDate;

    private String title;
    private String description;

    @Enumerated(value = EnumType.STRING)
    private JobPostType type;

    private Boolean urgent;
    
    @Enumerated(value = EnumType.STRING)
    private JobPostStatus status;

    @OneToMany
    private List<Skill> skillList;

    @ManyToOne(fetch = FetchType.EAGER)
    private JobCreator jobCreator;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public JobPost(Long id, Date timestamp, Date dueDate, String title, String description, JobPostType type,
            Boolean urgent, JobPostStatus status, Category category, JobCreator jobCreator) {
        this.id = id;
        this.timestamp = timestamp;
        this.dueDate = dueDate;
        this.title = title;
        this.description = description;
        this.type = type;
        this.urgent = urgent;
        this.status = status;
        this.jobCreator = jobCreator;
        this.category = category;
    }
}
