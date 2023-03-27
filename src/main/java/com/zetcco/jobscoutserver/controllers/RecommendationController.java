package com.zetcco.jobscoutserver.controllers;



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

import com.zetcco.jobscoutserver.services.RecommendationService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;


   @PostMapping("/request")
    public ResponseEntity<RecommendationDTO> addRecommendationRequest(@RequestBody RecommendationDTO recommendationDTO) {
        try{
            
            return new  ResponseEntity<RecommendationDTO>(recommendationService.addRecommendationRequest(recommendationDTO), HttpStatus.OK);
        } catch(DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    public ResponseEntity<RecommendationDTO> addRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        try{
            return new ResponseEntity<RecommendationDTO>(recommendationService.addRecommendation(recommendationDTO) , HttpStatus.OK);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    } 

    
    @PutMapping("/update")
    public ResponseEntity<RecommendationDTO> updateRecommendation(@RequestBody RecommendationDTO nwRecommendationDTO) {
        try{
            return new ResponseEntity<RecommendationDTO>(recommendationService.updateRecommendation(nwRecommendationDTO) , HttpStatus.OK);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }


    @DeleteMapping("/delete/{recommendationId}/{requesterId}")
    private void deleteRecommendation(@PathVariable Long recommendationId, @PathVariable Long requesterId) {
        try {
            recommendationService.deleteRecommendation(recommendationId, requesterId);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    
}
