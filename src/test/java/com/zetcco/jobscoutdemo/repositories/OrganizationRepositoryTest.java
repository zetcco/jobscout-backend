package com.zetcco.jobscoutdemo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;

@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Test
    public void saveNewOrganization() {
        Address address = Address.builder()
                                .number("41")
                                .street("Street")
                                .town("Town")
                                .city("City")
                                .province("Province")
                                .country("Country")
                                .build();
        Organization organization = new Organization("org@gmail.com", "orgpasswd", address);
        organizationRepository.save(organization);
    }

    @Test
    public void saveOrganizationProfile() {
        Organization organization = organizationRepository.findById(3L).orElseThrow();
        organization.setCompanyName("Creative Software");
        organization.setBusinessRegistration("https://..../...");
        organization.setDisplayPicture("New dp bro");
        organizationRepository.save(organization);
    }
}
