package com.zetcco.jobscoutserver.services;


import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.mappers.RecommendationMapper;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
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
    private UserService userService;

    @Autowired
    private RecommendationMapper recommendationMapper;

    @Autowired
    private UserMapper userMapper;

    public void addRecommendationRequest(Long responderId, Long requesterId) {
        JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();
        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();

        if(recommendationRequest.contains(requester))
            throw new DataIntegrityViolationException("Request already exitsts");

        recommendationRequest.add(requester);
        responder.setRecommendationRequests(recommendationRequest);
        jobCreatorRepository.save(responder);
    }

    public void addRecommendationRequest(Long responderId) {
        Long requesterId = userService.getAuthUser().getId();
        this.addRecommendationRequest(responderId, requesterId);
    }

    @Transactional
    public RecommendationDTO addRecommendation(Long responderId, Long requesterId, String content) throws NotFoundException {
        try {
            JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
            JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();
            
            List<JobSeeker> requesterList = responder.getRecommendationRequests();
            List<Recommendation> recommendationsList = requester.getRecommendations();

            if(requesterList.contains(requester)) {
                Recommendation recommendation = Recommendation.builder()
                                                        .content(content)
                                                        .responder(responder)
                                                        .build();
                recommendation = recommendationRepository.save(recommendation);

                recommendationsList.add(recommendation);
                requester.setRecommendations(recommendationsList);
                jobSeekerRepository.save(requester);

                requesterList.remove(requester);
                responder.setRecommendationRequests(requesterList);
                jobCreatorRepository.save(responder);

                return recommendationMapper.mapToDTO(recommendation);
            }
            else {
                throw new NotFoundException("No request found");
            }
        } catch (NoSuchElementException e) {
            throw new NotFoundException("User not found");
        }
    }

    public RecommendationDTO addRecommendation(RecommendationDTO recommendationDTO) throws NotFoundException {
        Long responderId = userService.getAuthUser().getId();
        return this.addRecommendation(
            responderId,
            recommendationDTO.getRequester().getId(),
            recommendationDTO.getContent()
        );
    }

    public RecommendationDTO updateRecommendation(RecommendationDTO nwRecommendationDTO, Long creatorId) throws AccessDeniedException, NotFoundException {
        Recommendation recommendation = recommendationRepository.findById(nwRecommendationDTO.getId()).orElseThrow(() -> new NotFoundException("Recommendation not found"));
        
        if(recommendation.getResponder().getId() == creatorId) {
            recommendation.setContent(nwRecommendationDTO.getContent());
            recommendation = recommendationRepository.save(recommendation);
        }
        else 
            throw new AccessDeniedException("You do not have permission to perform this action");

        return recommendationMapper.mapToDTO(recommendation);
    }

    public RecommendationDTO updateRecommendationByCreator(RecommendationDTO newRecommendationDTO) throws AccessDeniedException, NotFoundException {
        Long creatorId = userService.getAuthUser().getId();
        return this.updateRecommendation(newRecommendationDTO, creatorId);
    }


    public void deleteRecommendationRequest(Long responderId, Long requesterId) {
        JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();
        List<JobSeeker> recommendationRequest = responder.getRecommendationRequests();

        if(recommendationRequest.contains(requester)) {
            recommendationRequest.remove(requester);
            responder.setRecommendationRequests(recommendationRequest);
            jobCreatorRepository.save(responder);
        } else {
            throw new NotFoundException("Request not exitsts");
        }

        
    }

    public void deleteRecommendationRequest(Long requesterId) {
        Long responderId = userService.getAuthUser().getId();
        this.deleteRecommendationRequest(responderId, requesterId);
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

    public List<ProfileDTO> getRequestsByJobCreator(Long responderId) {
        JobCreator jobCreator = (JobCreator)userService.getUser(responderId);
        return userMapper.mapToDtos(jobCreator.getRecommendationRequests());
    }   

    public List<ProfileDTO> getRequests() {
        Long jobCreatorId = userService.getAuthUser().getId();
        return this.getRequestsByJobCreator(jobCreatorId);
    }

    public Boolean checkRecommendationRequestByCreator(Long jobCreatorId, Long jobSeekerId) {
        try {
            JobCreator jobCreator = (JobCreator)userService.getUser(jobCreatorId);
            JobSeeker jobSeeker = (JobSeeker)userService.getUser(jobSeekerId);
            List<JobSeeker> requests = jobCreator.getRecommendationRequests();
            return requests.contains(jobSeeker);
        } catch (ClassCastException e) {
            throw new NotFoundException("User not found");
        }
    }

    public Boolean checkRecommendationRequestByCreator(Long jobCreatorId) throws NotFoundException {
        Long jobSeekerId = userService.getAuthUser().getId();
        return this.checkRecommendationRequestByCreator(jobCreatorId, jobSeekerId);
    }
}
