package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RecommendationServiceTest {

    @Autowired
    private RecommendationService recommendationService;

    @Test
    void addRecommendation() {
        System.out.println(recommendationService.addRecommendation(4L, 7L));
    }
}
