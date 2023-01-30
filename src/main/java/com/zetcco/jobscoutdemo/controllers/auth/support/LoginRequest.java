package com.zetcco.jobscoutdemo.controllers.auth.support;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest {
    
    private String email;
    private String password;

}
