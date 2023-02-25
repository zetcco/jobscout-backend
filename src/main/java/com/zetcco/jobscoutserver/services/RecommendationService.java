package com.zetcco.jobscoutserver.services;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.Notification;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.boot.context.properties.PropertyMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Service    
public class RecommendationService {
    
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private ModelMapper modelMapper;

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

    private List<RecommendationDTO> mapRecommendations(List<Recommendation> recommendations) {
        return recommendations
                .stream()
                .map(recommendation -> propertyMapper.map(recommendation))
                .collect(Collectors.toList());
    }

    List<RecommendationDTO> getRecommendations(Long recommendationId, String contetnt, User responder) {
        List<Recommendation> recommendations = recommendationRepository.findByRequesterId(recommendationId);
        return this.mapRecommendations(recommendations);
    }

}