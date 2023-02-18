package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;

@Service
public class JobCreatorService {

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    public ProfileDTO requestForOrganization(Long jobCreatorId, Long organizationId) {
        Organization organization = organizationService.getOrganizationById(organizationId);
        JobCreator requestee = this.getJobCreatorById(jobCreatorId);
        List<JobCreator> request_list = organization.getJobCreatorRequests();
        request_list.add(requestee);
        organization.setJobCreatorRequests(request_list);

        return userService.getUser(organizationService.save(organization));
    }
    
    protected JobCreator getJobCreatorById(Long jobCreatorId) {
        return jobCreatorRepository.findById(jobCreatorId).orElseThrow();
    }
}
