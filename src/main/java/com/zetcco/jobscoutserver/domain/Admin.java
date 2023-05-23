package com.zetcco.jobscoutserver.domain;

import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class Admin extends User {

    private String firstName;
    private String lastName;

    public Admin(String email, String password) {
        super(email, password, Role.ROLE_ADMIN, null, null);
    }

    public Admin(String email, String password, String firstName, String lastName, String contact) {
        super(email, password, Role.ROLE_ADMIN, null, firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
