package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JobCreatorServiceTest {

    @Autowired
    private JobCreatorService jobCreatorService;

    @Test
    void testRequestForOrganization() {
        System.out.println(jobCreatorService.requestForOrganization(72L, 67L));
    }

}
