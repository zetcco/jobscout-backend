package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.mappers.RecommendationMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@SpringBootTest
public class RecommendationServiceTest {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public RecommendationMapper mapper;

    @Test
    void testAddRecommendationRequest() {
        JobCreator responder = jobCreatorRepository.findById(128L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(117L).orElseThrow();

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
    void testAddRecommendation() {
        JobCreator responder = jobCreatorRepository.findById(128L).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(117L).orElseThrow();
        
        List<JobSeeker> requesterList = responder.getRecommendationRequests();
        List<Recommendation> requestRecommendationList = requester.getRecommendation();
        
        if(requesterList.contains(requester)) {
            Recommendation nwRecommendation = Recommendation.builder()
                                                    .content("Recommended")
                                                    .responder(responder)
                                                    .build();
            nwRecommendation = recommendationRepository.save(nwRecommendation);
            System.out.println(nwRecommendation);

            requestRecommendationList.add(nwRecommendation);
            requester.setRecommendation(requestRecommendationList);
            jobSeekerRepository.save(requester);
            // System.out.println(jobSeekerRepository.save(requester));

            requesterList.remove(requester);
            responder.setRecommendationRequests(requesterList);
            jobCreatorRepository.save(responder);
            // System.out.println(jobCreatorRepository.save(responder));
        }else{
            throw new NotFoundException("Request not found");
        }
    }

    @Test
    void testUpdateRecommendation() {
        Recommendation nwRecommendation = recommendationRepository.findById(1L).orElseThrow();
        if(recommendationRepository.existsById(1L)) {
            nwRecommendation.setContent("Updated Recommended");
            recommendationRepository.save(nwRecommendation);
        }
        else {
            throw new NotFoundException("Recommendation Not Found");
        }
    }

    @Test
    void testDeleteRecommendation() {
        Recommendation recommendation = recommendationRepository.findById(1L).orElseThrow();
        
        JobSeeker requester = jobSeekerRepository.findById(117L).orElseThrow();
        List<Recommendation> requestRecommendationList = requester.getRecommendation();

        requestRecommendationList.remove(recommendation);
        requester.setRecommendation(requestRecommendationList);
        jobSeekerRepository.save(requester);

        recommendationRepository.delete(recommendation); 

    }

}