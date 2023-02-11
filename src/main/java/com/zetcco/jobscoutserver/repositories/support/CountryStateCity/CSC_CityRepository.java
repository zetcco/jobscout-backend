package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_City;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_State;

public interface CSC_CityRepository extends JpaRepository<CSC_City, Long> {
    public List<CSC_City> findByState(CSC_State state);
    public List<CSC_City> findByStateName(String name);
}
