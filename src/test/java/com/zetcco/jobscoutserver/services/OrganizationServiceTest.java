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
        // System.out.println(organizationService.searchOrganizationsByName("creative",
        // 1, 1));
    }

    @Test
    void testAddJobCreatorToOrganization() {
        // System.out.println(organizationService.addJobCreatorToOrganization(67L,
        // 72L));
    }

    @Test
    void testFetchJobCratorRequest() {

        // System.out.println(organizationService.fetchJobCreatorsRequest(4L));

    }

    // @Test
    // void testfetchOrganizationsRequest() {
    // System.out.println(organizationService.fetchOrganizationRequest(4L, 13L));

    // }

    @Test
    void testAcceptJobCreatorRequest() {
        // System.out.println(organizationService.acceptJobCreatorRequest(4L, 13L));
    }

    @Test
    void testRejectJobCreatorRequest() {
        // System.out.println(organizationService.rejectJobCreatorRequest(5L, 3L));
    }
}
