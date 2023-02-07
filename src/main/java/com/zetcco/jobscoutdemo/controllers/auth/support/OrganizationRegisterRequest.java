package com.zetcco.jobscoutdemo.controllers.auth.support;

import com.zetcco.jobscoutdemo.domain.support.Address;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrganizationRegisterRequest extends RegisterRequest {
    @Setter(AccessLevel.NONE)
    private String companyName;
    private String brFileName;

    public OrganizationRegisterRequest(String email, String password, Address address, String companyName) {
        super(email, password, address);
        this.companyName = companyName;
    }
}
