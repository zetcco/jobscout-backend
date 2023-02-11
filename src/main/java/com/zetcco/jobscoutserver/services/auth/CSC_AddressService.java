package com.zetcco.jobscoutserver.services.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_City;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_State;
import com.zetcco.jobscoutserver.repositories.support.CountryStateCity.CSC_CityRepository;
import com.zetcco.jobscoutserver.repositories.support.CountryStateCity.CSC_CountryRepository;
import com.zetcco.jobscoutserver.repositories.support.CountryStateCity.CSC_StateRepository;

@Service
public class CSC_AddressService {
    
    @Autowired
    private CSC_CountryRepository countryRepository;

    @Autowired
    private CSC_StateRepository stateRepository;

    @Autowired
    private CSC_CityRepository cityRepository;

    public List<CSC_Country> getCountries() {
        return countryRepository.findAll();
    }

    public List<CSC_State> getStates(String countryName) {
        return stateRepository.findByCountryName(countryName);
    }

    public List<CSC_City> getCities(String stateName) {
        return cityRepository.findByStateName(stateName);
    }
}
