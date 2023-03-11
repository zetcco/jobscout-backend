package com.zetcco.jobscoutserver.domain.support;

import java.util.List;

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
        List<String> values = List.of(number, street, town, city, province, country);
        String address = values.stream().reduce("", (addr, comp) -> addr + (StringUtils.isBlank(comp) ? "" : comp + ", "));
        return address.substring(0, address.length() - 2);
    }
}
