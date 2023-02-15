package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.OrganizationService;


@Controller
@RequestMapping("/organization")
public class OrganizationController {
    
    @Autowired
    private OrganizationService organizationService;

    @GetMapping("/search")
    public ResponseEntity<List<ProfileDTO>> searchOrganizations(@RequestParam("q") String keywords, @RequestParam("limit") int pageSize, @RequestParam("offset") int pageCount) {
        try {
            return new ResponseEntity<List<ProfileDTO>>(organizationService.searchOrganizationsByName(keywords, pageCount, pageSize), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
