package com.zetcco.jobscoutserver.domain.support.CountryStateCity;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "csc_states")
@Getter
public class CSC_State {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private CSC_Country country;

    @OneToMany(mappedBy = "state")
    private Set<CSC_City> cities;

    private String name;

}
