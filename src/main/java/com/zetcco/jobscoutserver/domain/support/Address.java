package com.zetcco.jobscoutserver.domain.support;

import io.micrometer.common.util.StringUtils;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Address {
    private String number;
    private String street;
    private String town;
    private String city;
    private String province;
    private String country;

    public String toString() {
        String addressStr = (StringUtils.isBlank(number) ? "" : number + ", ") +
                            (StringUtils.isBlank(street) ? "" : street + ", ") +
                            (StringUtils.isBlank(town) ? "" : town + ", ") +
                            (StringUtils.isBlank(city) ? "" : city + ", ") +
                            (StringUtils.isBlank(province) ? "" : province + ", ") +
                            (StringUtils.isBlank(country) ? "" : country);
        return addressStr;
    }
}
