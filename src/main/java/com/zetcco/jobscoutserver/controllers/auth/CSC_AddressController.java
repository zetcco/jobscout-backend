package com.zetcco.jobscoutserver.controllers.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_City;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_Country;
import com.zetcco.jobscoutserver.domain.support.CountryStateCity.CSC_State;
import com.zetcco.jobscoutserver.services.auth.CSC_AddressService;

@Controller
@RequestMapping("/address")
public class CSC_AddressController {

    @Autowired
    private CSC_AddressService addressService;
    
    @GetMapping("/countries")
    public ResponseEntity<List<CSC_Country>> getCountries() {
        return new ResponseEntity<List<CSC_Country>>(addressService.getCountries(), HttpStatus.OK);
    }

    @GetMapping("/states/{countryName}")
    public ResponseEntity<List<CSC_State>> getStates(@PathVariable String countryName) {
        System.out.println(countryName);
        return new ResponseEntity<List<CSC_State>>(addressService.getStates(countryName), HttpStatus.OK);
    }

    @GetMapping("/cities/{stateName}")
    public ResponseEntity<List<CSC_City>> getCities(@PathVariable String stateName) {
        return new ResponseEntity<List<CSC_City>>(addressService.getCities(stateName), HttpStatus.OK);
    }

}
