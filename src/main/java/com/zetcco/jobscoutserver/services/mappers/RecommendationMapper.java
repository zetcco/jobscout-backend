package com.zetcco.jobscoutserver.services.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Component
public class RecommendationMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RecommendationDTO mapToDTO(Recommendation recommendation) {
        return this.modelMapper.map(recommendation, RecommendationDTO.class);
    }

    public Recommendation mapToEntity(Recommendation recommendation) {
        return this.modelMapper.map(recommendation, Recommendation.class);
    }
}
