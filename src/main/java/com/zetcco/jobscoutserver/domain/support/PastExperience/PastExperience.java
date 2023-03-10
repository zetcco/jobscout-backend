package com.zetcco.jobscoutserver.domain.support.PastExperience;

import com.zetcco.jobscoutserver.domain.Organization;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PastExperience {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String startYear;
    private String endYear;
    
    @ManyToOne(optional = false)
    private JobTitle jobTitle;
    
    @ManyToOne(optional = false)
    private Organization organization;

}
