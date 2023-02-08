package com.zetcco.jobscoutserver.domain.support;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Address {
    private String number;
    private String street;
    private String town;
    private String city;
    private String province;
    private String country;
}
