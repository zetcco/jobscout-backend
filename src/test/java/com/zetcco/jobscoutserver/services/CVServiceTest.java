package com.zetcco.jobscoutserver.services;

import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestClientException;

import com.zetcco.jobscoutserver.services.support.CVService;

@SpringBootTest
public class CVServiceTest {

    @Autowired
    CVService cvService;

    @Test
    void testGenerateCV() {
        try {
            cvService.generateCV(117L, 1L);
        } catch (RestClientException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

}
