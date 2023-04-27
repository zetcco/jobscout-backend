package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobCreatorService {

    @Autowired
    private JobCreatorRepository jobCreatorRepository;
    
    // TODO: Fix CircularDependency without using setter injection
    @Autowired @Lazy
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('JOB_CREATOR')")
    public ProfileDTO requestForOrganization(Long organizationId) throws NotFoundException, DataIntegrityViolationException {
        Long jobCreatorId = ((User)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return this.requestForOrganization(organizationId, jobCreatorId);
    }

    ProfileDTO requestForOrganization(Long organizationId, Long jobCreatorId) throws NotFoundException, DataIntegrityViolationException {
        JobCreator requestee = this.getJobCreatorById(jobCreatorId);
        if (requestee.getOrganization() != null)
            throw new DataIntegrityViolationException("Remove already registered organization to place an request");
        Organization organization = organizationService.getOrganizationById(organizationId);
        List<JobCreator> request_list = organization.getJobCreatorRequests();
        request_list.add(requestee);
        organization.setJobCreatorRequests(request_list);
        try {
            Organization updatedOrganization = organizationService.save(organization);
            return userService.getUser(updatedOrganization);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityViolationException("Remove already existing request to place an request");
        }
    }
    
    protected JobCreator getJobCreatorById(Long jobCreatorId) throws NotFoundException {
        return jobCreatorRepository.findById(jobCreatorId).orElseThrow(() -> new NotFoundException("Job Creator not found"));
    }

    protected JobCreator save(JobCreator jobCreator) {
        return jobCreatorRepository.save(jobCreator);
    }
}
