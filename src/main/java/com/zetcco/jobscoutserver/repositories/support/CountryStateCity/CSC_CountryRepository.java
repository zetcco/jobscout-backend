package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;

public interface CSC_CountryRepository extends JpaRepository<CSC_Country, Long> {

    List<CSC_Country> findByName(String name);
    
}
