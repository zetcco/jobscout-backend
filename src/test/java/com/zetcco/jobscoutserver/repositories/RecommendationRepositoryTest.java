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
    public void saveRecommendation() {
        User responder = userRepository.findById(5L).orElseThrow();
        // User requester = userRepository.findById(2L).orElseThrow();

        Recommendation recommendation = Recommendation.builder()
                                                        .content("Bad-recommended")
                                                        .responder(responder)
                                                        .build();
        recommendationRepository.save(recommendation);
    }

    @Test
    public void saveAllStudent() {
        List<Recommendation> recommendationList = recommendationRepository.findAll();
        System.out.println("Recommendation  : " +recommendationList);
    }
    
    @Test
    public void addRecommendationRequest() {
        JobCreator responder = jobCreatorRepository.findById(124L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(132L).orElseThrow();

        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();
        if(recommendationRequest.contains(requester))
            throw new DataIntegrityViolationException("Request already exitsts");
        recommendationRequest.add(requester);
        responder.setRecommendationRequests(recommendationRequest);
        jobCreatorRepository.save(responder);
    }

       @Test
    public void addRecommendation() {
        JobCreator responder = jobCreatorRepository.findById(124L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(132L).orElseThrow();

        List<JobSeeker> requestRecommendation = responder.getRecommendationRequests();
        List<Recommendation> requestRecommendationList = requester.getRecommendations();
        
        if(requestRecommendation.contains(requester)) {
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
    public void getRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(1L).orElseThrow();
        System.out.println(recommendation);
    }

    @Test
    public void updateRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(1L).orElseThrow();
        recommendation.setContent("Not recommended");
        Recommendation updatedRecommendation = recommendationRepository.save(recommendation);
        System.out.println(updatedRecommendation);
    }

    @Test
    public void deleteRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(17L).orElseThrow();
        
        JobSeeker requester = jobSeekerRepository.findById(10L).orElseThrow();
        List<Recommendation> requestRecommendationList = requester.getRecommendations();

        requestRecommendationList.remove(recommendation);
        requester.setRecommendations(requestRecommendationList);
        jobSeekerRepository.save(requester);

        recommendationRepository.delete(recommendation); 

    }

}