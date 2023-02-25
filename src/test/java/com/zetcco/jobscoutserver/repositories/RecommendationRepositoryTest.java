package com.zetcco.jobscoutserver.repositories;


import java.util.List;

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
    public void saveRecommendationTest1() {
        User requester = userRepository.findById(1L).orElseThrow();
        User responder = userRepository.findById(2L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("recommended")
                                            .requester(requester)
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }

    @Test
    public void saveRecommendationTest2() {
        User requester = userRepository.findById(3L).orElseThrow();
        User responder = userRepository.findById(4L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("not-recommended")
                                            .requester(requester)
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }

    @Test
    public void saveRecommendationTest3() {
        User requester = userRepository.findById(3L).orElseThrow();
        User responder = userRepository.findById(1L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("weakly-recommended")
                                            .requester(requester)
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }


    @Test
    public void getRecommendationTest() {
        Recommendation recommendation = recommendationRepository.findById(1L).orElseThrow();
        System.out.println(recommendation);
    }

    // @Test
    // public void getRecommendationTestByRequester() {
    //     Recommendation recommendation = recommendationRepository.findByRequester(1L).
    //     System.out.println(recommendation);
    // }
        
    @Test
    public void updateRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(2L).orElseThrow();
        recommendation.setContent("highly recommended");

        User requester = userRepository.findById(1L).orElseThrow();
        recommendation.setRequester(requester);

        recommendationRepository.save(recommendation);
    }

    @Test
    public void deleteRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(3L).orElseThrow();
        recommendationRepository.delete(recommendation);

    }

    @Test
    public void findByRequesterId() {
        List<Recommendation> recommendations = recommendationRepository.findByRequesterId(1L);
        System.out.println(recommendations);
    }
}
