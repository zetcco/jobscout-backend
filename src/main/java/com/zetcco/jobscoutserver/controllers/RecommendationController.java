package com.zetcco.jobscoutserver.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.services.RecommendationService;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;

    // @GetMapping("/{requesterId}")
    // private List<RecommendationDTO> addRecommendation(@PathVariable Long requesterId) {
    //     return recommendationService.addRecommendation(requesterId);

    // } 

    @PostMapping("/")
    private RecommendationDTO addRecommendation(@RequestBody Recommendation recommendation) {
        return recommendationService.addRecommendation(recommendation);

    } 

    @DeleteMapping("/{recommendationId}")
    private void deleteRecommendation(@PathVariable Long recommendationId) {
        recommendationService.deleteRecommendation(recommendationId);
    }

    @PutMapping("/{recommendationId}")
    private RecommendationDTO updateRecommendation(@PathVariable Long recommendationId) {
       return recommendationService.updateRecommendation(recommendationId);
    }
}
