package com.zetcco.jobscoutserver.services.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Component
public class RecommendationMapper {

    @Autowired
    private UserMapper userMapper;

    public RecommendationDTO mapToDTO(Recommendation recommendation) {
        return new RecommendationDTO(
            recommendation.getReccomendationId(), 
            recommendation.getContent(),
            null,
            userMapper.mapToDto(recommendation.getResponder())
        );
    }

}
