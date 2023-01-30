package com.zetcco.jobscoutdemo.domain;

import com.zetcco.jobscoutdemo.domain.support.User;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
public class Organization extends User {
    private String companyName;
    private String businessRegistration;
}
