package com.zetcco.jobscoutserver.services;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    public ModelMapper modelMapper;

    private TypeMap<Recommendation, RecommendationDTO> propertyMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.propertyMapper = modelMapper.createTypeMap(Recommendation.class, RecommendationDTO.class);
    }

    public ProfileDTO addRecommendationRequest(Long responderId) {
        Long requester = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return addRecommendationRequest(responderId, requester);
    }

    private ProfileDTO addRecommendationRequest(Long responderId, Long requesterId) {
        JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();

        responder.getRecommendationRequests().contains(requester);
        List<JobSeeker> requestRecommendation = responder.getRecommendationRequests();
        if (!recommendationRepository.existsById(responderId))
            throw new DataIntegrityViolationException("Request already exitsts");
        requestRecommendation.add(requester);
        responder.setRecommendationRequests(requestRecommendation);
        jobCreatorRepository.save(responder);
        return userService.getUser(responderId);
    }

    public RecommendationDTO addRecommendation(RecommendationDTO recommendationDTO) {
        JobCreator responder = jobCreatorRepository.findById(recommendationDTO.getResponder().getId()).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(recommendationDTO.getRequester().getId()).orElseThrow();
        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();

        if(! recommendationRepository.existsById(recommendationDTO.getRecommendationId()))
            throw new DataIntegrityViolationException("Request already exitsts");
        
        List<Recommendation> requestRecommendationList = requester.getRecommendation();
        recommendationRequest.add(requester);
        requester.setRecommendation(requestRecommendationList);
        requester = jobSeekerRepository.save(requester);
        
        recommendationRequest.remove(requester);
        requester.setRecommendation(requestRecommendationList);
        jobCreatorRepository.save(responder);
        
        for(JobSeeker checkRecommendationRequest : recommendationRequest) {
            requestRecommendationList.remove(checkRecommendationRequest);
        }

        return null;
    }

    public void updateRecommendation(Recommendation recommendation) {
        recommendation.setContent(recommendation.getContent());
        recommendationRepository.save(recommendation);
    }

    public void deleteRecommendation(Recommendation recommendation) {
        recommendationRepository.delete(recommendation);
    }

}
