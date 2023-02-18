package com.zetcco.jobscoutserver.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Gender;
import com.zetcco.jobscoutserver.domain.support.NameTitle;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class JobCreator extends User {

    @Enumerated(EnumType.STRING)
    private NameTitle title;
    private String firstName;
    private String lastName;
    private String contact;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date dob;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public JobCreator(String email, String password, Address address) {
        super(email, password, Role.ROLE_JOB_CREATOR, address);
    }

    public JobCreator(String email, String password, Address address, NameTitle title,
            String firstName, String lastName, String contact, Date dob, Gender gender) {
        super(email, password, Role.ROLE_JOB_CREATOR, address);
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }
}