package com.zetcco.jobscoutserver.services.support;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class RecommendationDTO {

    private Long id;
    private String content;
    private ProfileDTO requester;
    private ProfileDTO responder;

}
