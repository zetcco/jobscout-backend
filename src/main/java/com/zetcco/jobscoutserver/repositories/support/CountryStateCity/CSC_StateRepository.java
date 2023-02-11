package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_State;

public interface CSC_StateRepository extends JpaRepository<CSC_State, Long> {
    public List<CSC_State> findByCountry(CSC_Country country);
    public List<CSC_State> findByCountryName(String name);
}
