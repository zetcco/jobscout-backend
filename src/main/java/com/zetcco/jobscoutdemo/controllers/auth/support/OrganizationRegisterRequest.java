package com.zetcco.jobscoutdemo.controllers.auth.support;

import com.zetcco.jobscoutdemo.domain.support.Address;

import lombok.Getter;

@Getter
public class OrganizationRegisterRequest extends RegisterRequest {
    private String companyName;
    private String businessRegistration;

    public OrganizationRegisterRequest(String email, String password, Address address, String companyName, String businessRegistration) {
        super(email, password, address);
        this.businessRegistration = businessRegistration;
        this.companyName = companyName;
    }
}
