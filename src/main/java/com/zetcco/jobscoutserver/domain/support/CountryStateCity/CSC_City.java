package com.zetcco.jobscoutserver.domain.support.CountryStateCity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "csc_cities")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CSC_City {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false)
    private CSC_State state;

    private String name;

}
