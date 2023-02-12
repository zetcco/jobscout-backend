package com.zetcco.jobscoutserver.controllers.auth.support;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationResponse {

    @Setter(AccessLevel.NONE)
    private String jwtToken;

}
