package com.zetcco.jobscoutserver.domain.questionary;

import com.zetcco.jobscoutserver.domain.JobSeeker;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionaryAttempt {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Questionary questionary;

    @OneToOne(fetch = FetchType.LAZY)
    private JobSeeker jobSeeker;

    private Integer attempts;
    private Float score;
    private Boolean isPublic;
    
}
