package com.zetcco.jobscoutserver.domain;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Gender;
import com.zetcco.jobscoutserver.domain.support.NameTitle;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
    
    @OneToMany
    private List<JobPost> jobPost;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<JobSeeker> recommendationRequests;

    public JobCreator(String email, String password, Address address) {
        super(email, password, Role.ROLE_JOB_CREATOR, address, null);
    }

    public JobCreator(String email, String password, Address address, NameTitle title,
            String firstName, String lastName, String contact, Date dob, Gender gender) {
        super(email, password, Role.ROLE_JOB_CREATOR, address, firstName + " " + lastName);
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }

}