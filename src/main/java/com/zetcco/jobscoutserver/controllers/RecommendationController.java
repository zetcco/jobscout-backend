package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.RecommendationService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    
    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/requests")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<List<ProfileDTO>> getRecommendationRequests() {
        try {
            return new ResponseEntity<List<ProfileDTO>>(recommendationService.getRequests(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/request")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<?> addRecommendationRequest(@RequestBody Map<String, Long> request) {
        try{
            Long responderId = request.get("responderId");
            recommendationService.addRecommendationRequest(responderId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/check-request/{jobCreatorId}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Boolean> checkRecommendationRequest(@PathVariable Long jobCreatorId) {
        try{
            return new ResponseEntity<Boolean>(recommendationService.checkRecommendationRequestByCreator(jobCreatorId), HttpStatus.OK);
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<RecommendationDTO> addRecommendation(@RequestBody RecommendationDTO recommendationDTO) {
        try{
            return new ResponseEntity<RecommendationDTO>(recommendationService.addRecommendation(recommendationDTO) , HttpStatus.OK);
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(Exception e){
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    } 

    
    @PutMapping("/update")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<RecommendationDTO> updateRecommendation(@RequestBody RecommendationDTO nwRecommendationDTO) {
        try{
            return new ResponseEntity<RecommendationDTO>(recommendationService.updateRecommendationByCreator(nwRecommendationDTO) , HttpStatus.OK);
        } catch(AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
