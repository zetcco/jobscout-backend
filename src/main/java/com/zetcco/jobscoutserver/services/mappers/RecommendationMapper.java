package com.zetcco.jobscoutserver.services.mappers;

import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@Component
public class RecommendationMapper {

    public RecommendationDTO mapToDTO(Recommendation recommendation) {
        RecommendationDTO recommendationDTO = RecommendationDTO.builder()
                                                            .content(recommendation.getContent())
                                                            .responder(recommendation.getResponder())
                                                            .build();
        return recommendationDTO;
    }
}
