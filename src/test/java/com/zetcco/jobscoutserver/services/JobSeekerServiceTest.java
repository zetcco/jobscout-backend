package com.zetcco.jobscoutserver.services;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

@SpringBootTest
public class JobSeekerServiceTest {

    @Autowired
    JobSeekerService jobSeekerService;

    @Test
    void testGenerateCV() {
        try {
            jobSeekerService.generateCV(117L, 1L);
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
