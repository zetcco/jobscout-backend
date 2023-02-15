package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Test
    void testSearchOrganizationsByNameFTS() {
        System.out.println(organizationService.searchOrganizationsByNameFTS("not creative", 0, 1));
        System.out.println(organizationService.searchOrganizationsByNameFTS("not creative", 1, 1));
    }

    @Test
    void testSearchOrganizationsByName() {
        System.out.println(organizationService.searchOrganizationsByName("not creative", 0, 1));
        // System.out.println(organizationService.searchOrganizationsByName("creative", 1, 1));
    }

}
