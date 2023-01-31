package com.zetcco.jobscoutdemo.controllers.auth.support;

import com.zetcco.jobscoutdemo.domain.support.Address;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    
    private String email;
    private String password;
    private Address address;

}
