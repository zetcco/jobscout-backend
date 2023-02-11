
package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_State;

@SpringBootTest
public class CSC_StateRepositoryTest {

    @Autowired
    private CSC_CountryRepository countryRepository;

    @Autowired
    private CSC_StateRepository stateRepository;

    @Test
    void testFindByCountry() {
        List<CSC_Country> countries = countryRepository.findByName("Sri Lanka");
        List<CSC_State> states = stateRepository.findByCountry(countries.get(0));
        for (CSC_State csc_State : states) {
            System.out.println(csc_State.getName());
        }
    }

    @Test
    void testFindByCountryName() {
        List<CSC_State> states = stateRepository.findByCountryName("Sri Lanka");
        for (CSC_State csc_State : states) {
            System.out.println(csc_State.getName());
        }
    }
}
