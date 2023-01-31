package com.zetcco.jobscoutdemo.controllers.auth.support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    private String name;
    private String email;
    private String role;
    
    @Setter(AccessLevel.NONE)
    private String jwtToken;

    private String status;
}
