package com.zetcco.jobscoutdemo.domain;

import com.zetcco.jobscoutdemo.domain.support.Address;
import com.zetcco.jobscoutdemo.domain.support.Role;
import com.zetcco.jobscoutdemo.domain.support.User;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Organization extends User {
    private String companyName;
    private String businessRegistration;
    private String logo;

    public Organization(String email, String password, Address address) {
        super(email, password, Role.ROLE_ORGANIZATION, address);
    }

    public Organization(String email, String password, Address address, String companyName, String businessRegistration) {
        super(email, password, Role.ROLE_ORGANIZATION, address);
        this.companyName = companyName;
        this.businessRegistration = businessRegistration;
    }
}
