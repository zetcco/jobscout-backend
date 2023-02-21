package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;

@RestController
@RequestMapping("/recommendation")
public class RecommendationController {
    
    @Autowired
    private RecommendationRepository recommendationRepository;

    @GetMapping("/all")
    private List<Recommendation> gRecommendations() {
        return null;

    } 
}
