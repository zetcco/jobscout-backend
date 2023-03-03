package com.zetcco.jobscoutserver.services.support;

import com.zetcco.jobscoutserver.domain.support.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecommendationDTO {

    private Long recommendationId;
    private String content;
    private User requester;
    private User responder;

    

}
