package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;

@SpringBootTest
public class CSC_CountryRepositoryTest {
    
    @Autowired
    private CSC_CountryRepository repository;

    @Test
    public void getCountryByName() {
        List<CSC_Country> countries = repository.findByName("Sri Lanka");
        for (CSC_Country csc_Country : countries) {
            System.out.println(csc_Country.getName());
        }
    }

}
