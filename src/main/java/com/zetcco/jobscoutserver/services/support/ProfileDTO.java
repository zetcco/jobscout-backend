package com.zetcco.jobscoutserver.services.support;

// import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.Role;

import lombok.Data;

@Data
public class ProfileDTO {
    private Long id;
    private String email;
    private Role role;
    private String displayName;
    // private Address address;
    private String displayPicture;
}
