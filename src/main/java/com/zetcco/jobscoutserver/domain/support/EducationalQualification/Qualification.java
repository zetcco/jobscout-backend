package com.zetcco.jobscoutserver.domain.support.EducationalQualification;

import com.zetcco.jobscoutserver.domain.JobSeeker;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Qualification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    private JobSeeker jobSeeker;

    private String startYear;
    private String endYear;
    
    @ManyToOne
    private Institute institute;
    
    @ManyToOne
    private Degree degree;

}
