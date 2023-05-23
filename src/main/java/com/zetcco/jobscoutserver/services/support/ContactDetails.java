package com.zetcco.jobscoutserver.services.support;

import com.zetcco.jobscoutserver.domain.support.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetails {
    Role role;
    String email;
    String phone;
    String address;
}
