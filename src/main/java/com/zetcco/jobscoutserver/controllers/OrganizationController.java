package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.sym.Name;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.services.OrganizationService;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import io.micrometer.core.instrument.Meter.Id;

@Controller
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/search")
    public ResponseEntity<List<ProfileDTO>> searchOrganizations(@RequestParam("q") String keywords,
            @RequestParam("limit") int pageSize, @RequestParam("offset") int pageCount) {
        try {
            return new ResponseEntity<List<ProfileDTO>>(
                    organizationService.searchOrganizationsByName(keywords, pageCount, pageSize), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/add-creator")
    public ResponseEntity<List<ProfileDTO>> addCreatorToOrganization(@RequestBody Map<String, Long> request) {
        try {
            Long jobCreatorId = request.get("id");
            return new ResponseEntity<List<ProfileDTO>>(organizationService.addJobCreatorToOrganization(jobCreatorId),
                    HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Already registered on an Organization. Remove it to request again for joining to an Organization");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/join-request")
    public ResponseEntity<List<ProfileDTO>> fetchJobCreatorsRequest() {
        try {
            return new ResponseEntity<List<ProfileDTO>>(organizationService.fetchJobCreatorsRequest(),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/accept-request")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<List<ProfileDTO>> acceptJobCreatorRequest(@PathVariable Map<String, Long> request) {
        try {
            Long jobCreatorId = request.get("jobCreatorId");
            organizationService.acceptJobCreatorRequest(jobCreatorId);
            return new ResponseEntity<List<ProfileDTO>>(HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Already registered on an Organization. Remove it to request again for joining to an Organization");
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/rejecet-request")
    @PreAuthorize("hasRole('ORGANIZATION')")
    public ResponseEntity<List<ProfileDTO>> rejectJobCreatorRequest(@RequestBody Map<String, Long> requeest) {
        try {
            Long jobCreatorId = requeest.get("jobCreatorId");
            organizationService.acceptJobCreatorRequest(jobCreatorId);
            return new ResponseEntity<List<ProfileDTO>>(HttpStatus.OK);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

}
