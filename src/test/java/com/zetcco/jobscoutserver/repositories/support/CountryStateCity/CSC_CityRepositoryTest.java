package com.zetcco.jobscoutserver.repositories.support.CountryStateCity;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_City;

@SpringBootTest
public class CSC_CityRepositoryTest {

    @Autowired
    private CSC_CityRepository cityRepository;

    @Test
    void testFindByState() {
        List<CSC_City> cities = cityRepository.findByStateName("Matara");
        for (CSC_City csc_City : cities) {
            System.out.println(csc_City.getName());
        }
    }

}
