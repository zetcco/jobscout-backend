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
public class JobSeeker extends User {
    private String firstName;
    private String lastName;
}
