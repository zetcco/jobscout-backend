package com.zetcco.jobscoutserver.repositories;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.services.support.NotFoundException;


@SpringBootTest
public class RecommendationRepositoryTest {

    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Test
    public void saveRecommendationTest1() {
        // User requester = userRepository.findById(1L).orElseThrow();
        User responder = userRepository.findById(2L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("recommended")
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }

    @Test
    public void saveRecommendationTest2() {
        // User requester = userRepository.findById(3L).orElseThrow();
        User responder = userRepository.findById(4L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("not-recommended")
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }

    @Test
    public void saveRecommendationTest3() {
        // User requester = userRepository.findById(3L).orElseThrow();
        User responder = userRepository.findById(1L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                            .content("weakly-recommended")
                                            .responder(responder)
                                            .build();
        
        recommendationRepository.save(recommendation);
    }


    @Test
    public void getRecommendationTest() {
        Recommendation recommendation = recommendationRepository.findById(1L).orElseThrow();
        System.out.println(recommendation);
    }

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
        Recommendation recommendation = recommendationRepository.findById(2L).orElseThrow();
        recommendationRepository.delete(recommendation);

    }

    @Test
    public void addRecommendationRequest() {
        JobCreator responder = jobCreatorRepository.findById(5L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(6L).orElseThrow();

        List<JobSeeker> requestRecommendation = responder.getRequestRecommendation();
        if (requestRecommendation.contains(requester))
            throw new DataIntegrityViolationException("Request already exitsts");
            requestRecommendation.add(requester);
        responder.setRequestRecommendation(requestRecommendation);
        jobCreatorRepository.save(responder);
    }

    @Test
    public void addRecommendation() {
        JobCreator responder = jobCreatorRepository.findById(5L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(6L).orElseThrow();

        List<JobSeeker> requestRecommendation = responder.getRequestRecommendation();
        if(requestRecommendation.contains(requester)) {
                List<Recommendation> requestRecommendationList = requester.getRecommendations();
                Recommendation recommendation = Recommendation.builder()
                                                    .content("Highly-recommended")
                                                    .responder(responder)
                                                    .build();
                recommendation = recommendationRepository.save(recommendation);

                requestRecommendationList.add(recommendation);
                requester.setRecommendations(requestRecommendationList);
                jobSeekerRepository.save(requester);

                requestRecommendation.remove(requester);
                responder.setRecommendationRequests(requestRecommendation);
                jobCreatorRepository.save(responder);


        }else{
            throw new NotFoundException("Request not found");
        }
    }
    
    @Test
    public void searchRecommendationById() {
        Recommendation recommendation = recommendationRepository.findByRecommendationId(4L);
        System.out.println(recommendation);
    }

    
}
