package com.zetcco.jobscoutserver.domain;

import com.zetcco.jobscoutserver.domain.support.ApplicationStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ApplicationStatus status;
    
    @OneToOne
    private JobSeeker jobSeeker;

    @OneToOne
    private JobPost jobPost;
    
}
