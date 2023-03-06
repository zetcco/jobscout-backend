package com.zetcco.jobscoutserver.controllers;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.Recommendation;
import com.zetcco.jobscoutserver.repositories.RecommendationRepository;
import com.zetcco.jobscoutserver.services.RecommendationService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;

    @Autowired
    private RecommendationRepository recommendationRepository;

   @PostMapping("/{jobCreatorId}")
    public ResponseEntity<ProfileDTO> addRecommendationRequest(@RequestBody Map<String, Long> responder) {
        try{
            Long responderId = responder.get("id");
            return new  ResponseEntity<ProfileDTO>(recommendationService.addRecommendationRequest(responderId), HttpStatus.OK);
        } catch(DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<RecommendationDTO> addRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        try{
            return new ResponseEntity<RecommendationDTO>(recommendationService.addRecommendation(recommendationDTO) , HttpStatus.OK);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    } 

    
    @PutMapping("/update/{recommendationId}")
    private void updateRecommendation(@PathVariable Recommendation recommendation) {
        recommendationRepository.save(recommendation);
    }


    @DeleteMapping("/delete/{recommendationId}")
    private void deleteRecommendation(@PathVariable("recommendationId") Long recommendationId) {
        recommendationRepository.deleteById(recommendationId);
    }

    
}
