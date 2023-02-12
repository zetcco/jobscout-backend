package com.zetcco.jobscoutserver.controllers.support;

import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Role;

import lombok.Data;

@Data
public class ProfileDTO {
    private Long id;
    private String email;
    private Role role;
    private Address address;
    private String displayPicture;
}
