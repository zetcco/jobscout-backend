package com.zetcco.jobscoutdemo.controllers.auth.support;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zetcco.jobscoutdemo.domain.support.Address;
import com.zetcco.jobscoutdemo.domain.support.Gender;
import com.zetcco.jobscoutdemo.domain.support.NameTitle;

import lombok.Getter;

@Getter
public class JobCreatorRegistrationRequest extends RegisterRequest {
    
    private NameTitle title;
    private String firstName;
    private String lastName;
    private String contact;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Date dob;
    private Gender gender;

    public JobCreatorRegistrationRequest(String email, String password, Address address, NameTitle title,
            String firstName, String lastName, String contact, Date dob, Gender gender) {
        super(email, password, address);
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.contact = contact;
        this.dob = dob;
        this.gender = gender;
    }

}
