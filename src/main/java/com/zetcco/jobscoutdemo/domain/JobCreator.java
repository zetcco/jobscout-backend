package com.zetcco.jobscoutdemo.domain;

import com.zetcco.jobscoutdemo.domain.support.Address;
import com.zetcco.jobscoutdemo.domain.support.NameTitle;
import com.zetcco.jobscoutdemo.domain.support.Role;
import com.zetcco.jobscoutdemo.domain.support.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private NameTitle title;
    private String firstName;
    private String lastName;

    public JobCreator(String email, String password, Address address) {
        super(email, password, Role.ROLE_JOB_CREATOR, address);
    }
}