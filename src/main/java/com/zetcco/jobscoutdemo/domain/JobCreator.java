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
@NoArgsConstructor
@AllArgsConstructor
public class JobCreator extends User {
    private String firstName;
    private String lastName;

    public JobCreator(String email, String password, Address address) {
        super(email, password, Role.ROLE_JOB_CREATOR, address);
    }
}