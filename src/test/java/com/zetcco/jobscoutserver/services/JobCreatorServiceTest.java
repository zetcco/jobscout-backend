package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.services.support.NotFoundException;

@SpringBootTest
public class JobCreatorServiceTest {

    @Autowired
    private JobCreatorService jobCreatorService;

    @Test
    void testRequestForOrganization() {
        try {
            System.out.println(jobCreatorService.requestForOrganization(67L, 72L));
        } catch (NotFoundException e) {
            System.out.println(e);
        }
    }

}
