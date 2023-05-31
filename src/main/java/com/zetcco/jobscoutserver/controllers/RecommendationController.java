package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.RecommendationService;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.RecommendationDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.BadRequestException;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

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

    @DeleteMapping("/deleterequest")
    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ResponseEntity<Void> deleteRecommendationRequest(@RequestBody Map<String, Long> deleteRequest) {
        try{
            Long requesterId = deleteRequest.get("requesterId");
            recommendationService.deleteRecommendationRequest(requesterId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }catch(Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteRecommendationRequest(@RequestParam(defaultValue = "request") String type, @RequestParam(required = false) Long recommendation, @RequestParam(required = false) Long requester, @RequestParam(required = false) Long responder) {
        try{
            System.out.println(type);
            if (type.equals("recommendation")) {
                if (requester != null)
                    recommendationService.deleteRecommendation(recommendation, requester);
                else
                    throw new BadRequestException("Missing parameter. 'requester' not found.");
            } else if (type.equals("request")) {
                if (requester != null)
                    recommendationService.deleteRecommendationRequestByRequesterId(requester);
                else if (responder != null)
                    recommendationService.deleteRecommendationRequestByResponderId(responder);
                else
                    throw new BadRequestException("Missing parameters. 'responder' or 'requester' not found.");
            }
            else
                throw new BadRequestException("Invalid parameter 'type'");

            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch(NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch(BadRequestException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
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

    @GetMapping("/sent-requests")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<List<ProfileDTO>> getSentRequests() {
        try {
            return new ResponseEntity<List<ProfileDTO>>(recommendationService.getSentRequests(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
