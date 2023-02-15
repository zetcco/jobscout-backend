package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrganizationServiceTest {

    @Autowired
    private OrganizationService organizationService;

    @Test
    void testSearchOrganizationsByName() {
        System.out.println(organizationService.searchOrganizationsByName("not creative", 0, 1));
        System.out.println(organizationService.searchOrganizationsByName("not creative", 1, 1));
    }

}
