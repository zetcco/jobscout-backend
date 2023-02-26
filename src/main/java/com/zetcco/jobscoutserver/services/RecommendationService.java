package com.zetcco.jobscoutserver.services;



import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Service    
public class RecommendationService {

    private JobCreatorRepository jobCreatorRepository;
    private JobSeekerRepository jobSeekerRepository;

    
    @Autowired
    private static RecommendationRepository recommendationRepository;

    private TypeMap<Recommendation, RecommendationDTO> propertyMapper;

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.propertyMapper = modelMapper.createTypeMap(Recommendation.class, RecommendationDTO.class);
    }
    
    protected RecommendationDTO getRecommendation(Long recommendationId) {
        Recommendation recommendation = recommendationRepository.findById(recommendationId).orElseThrow();
        return this.mapRecommendation(recommendation);
    } 

    private RecommendationDTO mapRecommendation(Recommendation recommendation) {
        propertyMapper.addMapping(src -> src.getRecommendationId(), (dest, v) -> dest.setRecommendationId((Long)v));
        RecommendationDTO recommendationDTO = propertyMapper.map(recommendation);
        return recommendationDTO;
    }


    public RecommendationDTO addRecommendation(Recommendation recommendation) {
        Recommendation newRecommendation = recommendationRepository.save(recommendation);
        return this.mapRecommendation(newRecommendation);
    }

    public void deleteRecommendation(Long recommendationId) {
        recommendationRepository.deleteById(recommendationId);
        // Recommendation recommendation = recommendationRepository.findById(recommendationId).orElseThrow();
    }

    public RecommendationDTO updateRecommendation(Long recommendationId) {
        Recommendation updatedRecommendation = recommendationRepository.findById(recommendationId).orElseThrow();
        return this.mapRecommendation(updatedRecommendation);
    }
    
    public RecommendationDTO addRecommendation(Long requesterId, Long responderId) {
        JobCreator responder = jobCreatorRepository.findById(responderId).orElseThrow();
        JobSeeker requester = jobSeekerRepository.findById(requesterId).orElseThrow();


        List<JobSeeker> requestRecommendation = responder.getRequestRecommendation();
        // if (requestRecommendation.contains(requester))
        //     throw new DataIntegrityViolationException("Request already exitsts");
        requestRecommendation.add(requester);
        responder.setRequestRecommendation(requestRecommendation);
        jobCreatorRepository.save(responder);
        return this.getRecommendation(responderId);
    }
}