package com.zetcco.jobscoutserver.services;


import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.mappers.RecommendationMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

import jakarta.transaction.Transactional;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    public ModelMapper modelMapper;

    @Autowired
    public RecommendationMapper mapper;

    public RecommendationDTO addRecommendationRequest(RecommendationDTO recommendationDTO) throws NotFoundException{
        // Long requesterId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Long requesterId = recommendationDTO.getRequester().getId();
        Long responderId = recommendationDTO.getResponder().getId();
        // System.out.println(addRecommendationRequest(responderId, requesterId));
        return this.addRecommendationRequest(responderId, requesterId);
    }

    RecommendationDTO addRecommendationRequest(Long responderId, Long requesterId) {
        JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();
        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();

        if(recommendationRequest.contains(requester))
            throw new DataIntegrityViolationException("Request already exitsts");

        recommendationRequest.add(requester);
        responder.setRecommendationRequests(recommendationRequest);
        jobCreatorRepository.save(responder);

        return null;
    }

    @Transactional
    public RecommendationDTO addRecommendation(RecommendationDTO recommendationDTO) {
        JobCreator responder = jobCreatorRepository.findById(recommendationDTO.getResponder().getId()).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(recommendationDTO.getRequester().getId()).orElseThrow();
        
        List<JobSeeker> requesterList = responder.getRecommendationRequests();
        List<Recommendation> requestRecommendationList = requester.getRecommendations();

        if(requesterList.contains(requester)) {
            Recommendation recommendation = Recommendation.builder()
                                                    .content(recommendationDTO.getContent())
                                                    .responder(responder)
                                                    .build();
            recommendation = recommendationRepository.save(recommendation);

            requestRecommendationList.add(recommendation);
            requester.setRecommendations(requestRecommendationList);
            jobSeekerRepository.save(requester);

            requesterList.remove(requester);
            responder.setRecommendationRequests(requesterList);
            jobCreatorRepository.save(responder);

            return null;
        }
        else {
            throw new NotFoundException("No request found");
        }
    }

    public RecommendationDTO updateRecommendation(RecommendationDTO nwRecommendationDTO) {
        Recommendation updatedRecommendation = recommendationRepository.findById(nwRecommendationDTO.getRecommendationId()).orElseThrow();
        
        if(recommendationRepository.existsById(nwRecommendationDTO.getRecommendationId())) {
            updatedRecommendation.setContent(nwRecommendationDTO.getContent());
            recommendationRepository.save(updatedRecommendation);
 
        }
        else {
             addRecommendation(nwRecommendationDTO);
        }
        return null;
    }

    public void deleteRecommendation(Long recommendationId, Long requesterId) {
        Recommendation recommendationDelete = recommendationRepository.findById(recommendationId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();
        List<Recommendation> requestRecommendationList = requester.getRecommendations();

        requestRecommendationList.remove(recommendationDelete);
        requester.setRecommendations(requestRecommendationList);
        jobSeekerRepository.save(requester);

        recommendationRepository.delete(recommendationDelete);
    }   

}
