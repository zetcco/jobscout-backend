package com.zetcco.jobscoutserver.domain.support.CountryStateCity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "csc_countries")
@Getter
public class CSC_Country {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @OneToMany(mappedBy = "country")
    private Set<CSC_State> states;

}
