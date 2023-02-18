package com.zetcco.jobscoutserver.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.JobCreatorService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Controller
@RequestMapping("/jobcreator")
public class JobCreatorController {

    @Autowired
    private JobCreatorService jobCreatorService;

    @PostMapping("/request-organization")
    public ResponseEntity<ProfileDTO> requestForOrganization(@RequestBody Map<String, Long> request) {
        try {
            Long organizationId = request.get("id");
            System.out.println(organizationId);
            return new ResponseEntity<ProfileDTO>(jobCreatorService.requestForOrganization(organizationId), HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
