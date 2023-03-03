package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

import jakarta.transaction.Transactional;

@SpringBootTest
public class RecommendationServiceTest {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;


    @Test
    void testAddRecommendationRequest() {
        JobCreator responder = jobCreatorRepository.findById(5L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(6L).orElseThrow();

        List<JobSeeker> requestRecommendation = responder.getRecommendationRequests();
        if (!requestRecommendation.contains(requester)) {
            requestRecommendation.add(requester);
            responder.setRecommendationRequests(requestRecommendation);
            jobCreatorRepository.save(responder);
        }else {
            throw new DataIntegrityViolationException("Request already exitsts");
        }
    }

    @Test
    @Transactional
    void testAddRecommendation() {
        JobCreator responder = jobCreatorRepository.findById(5L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(6L).orElseThrow();
        
        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();
        if(recommendationRequest.contains(requester)) {
            List<Recommendation> requestRecommendationList = requester.getRecommendation();
            Recommendation recommendation = Recommendation.builder()
                                                    .content("Recommended")
                                                    .responder(responder)
                                                    .build();
            recommendation = recommendationRepository.save(recommendation);
            
            requestRecommendationList.add(recommendation);
            requester.setRecommendation(requestRecommendationList);
            jobSeekerRepository.save(requester);

            recommendationRequest.remove(requester);
            responder.setRecommendationRequests(recommendationRequest);
            jobCreatorRepository.save(responder);
        }else{
            throw new NotFoundException("Request not found");
        }
    }

    @Test
    void testUpdateRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(2L).orElseThrow();
        recommendation.setContent("very recommended");
        recommendationRepository.save(recommendation);
    }

    @Test
    void testDeleteRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(2L).orElseThrow();
        recommendationRepository.delete(recommendation);
    }

}
