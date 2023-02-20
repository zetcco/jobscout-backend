package com.zetcco.jobscoutserver.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.domain.support.User;

@SpringBootTest
public class RecommendationRepositoryTest {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    

    @Test
    public void saveRecommendation() {
        User requester = userRepository.findById(1L).orElseThrow();
        User responder = userRepository.findById(2L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("recommended")
                                            .requester(requester)
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }
        
}
