package com.zetcco.jobscoutserver.services;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private ModelMapper modelMapper;

    // TODO: Fix CircularDependency without using setter injection
    @Autowired
    @Lazy
    private JobCreatorService jobCreatorService;

    public List<ProfileDTO> searchOrganizationsByName(String name, int pageCount, int pageSize) {
        if (name.equals(""))
            throw new IllegalArgumentException("Wrong Parameters");
        Pageable page = PageRequest.of(pageCount, pageSize);
        List<Organization> organizations = organizationRepository.findByCompanyNameContainingIgnoreCase(name, page)
                .getContent();
        List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
        for (Organization organization : organizations) 
            profiles.add(userService.getUserProfileDTO(organization.getId()));
        return profiles;
    }

    public List<ProfileDTO> searchOrganizationsByNameFTS(String name, int pageCount, int pageSize) {
        String keywords = name.replace(' ', '&');
        Pageable page = PageRequest.of(pageCount, pageSize);
        List<Organization> organizations = organizationRepository.findOrganizationByNameFTS(keywords, page)
                .getContent();
        List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
        for (Organization organization : organizations)
            profiles.add(modelMapper.map(organization, ProfileDTO.class));
        return profiles;
    }

    @PreAuthorize("hasRole('ORGANIZATION')")
    public List<ProfileDTO> addJobCreatorToOrganization(Long jobCreatorId) throws NotFoundException {
        Long organizationId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        return this.addCreatorToOrganization(organizationId, jobCreatorId);
    }

    @Transactional
    List<ProfileDTO> addCreatorToOrganization(Long organizationId, Long jobCreatorId)
            throws NotFoundException, DataIntegrityViolationException {
        Organization organization = this.getOrganizationById(organizationId);
        List<JobCreator> jobCreators = organization.getJobCreators();
        List<JobCreator> request_list = organization.getJobCreatorRequests();
        JobCreator requestee = jobCreatorService.getJobCreatorById(jobCreatorId);

        if (request_list.contains(requestee)) {
            jobCreators.add(requestee);
            request_list.remove(requestee);

            organization.setJobCreatorRequests(request_list);
            organization.setJobCreators(jobCreators);
            requestee.setOrganization(organization);

            ProfileDTO updatedOrganization = userService.getUserProfileDTO(this.save(organization).getId());
            ProfileDTO updatedJobCreator = userService.getUserProfileDTO(jobCreatorService.save(requestee).getId());

            return List.of(updatedJobCreator, updatedOrganization);
        } else {
            throw new NotFoundException("No request from Job Creator found");
        }
    }

    Organization getOrganizationById(Long organizationId) throws NotFoundException {
        return organizationRepository.findById(organizationId)
                .orElseThrow(() -> new NotFoundException("Organization not Found"));
    }

    public List<ProfileDTO> fetchJobCreatorsRequest() {
        Long organizationId = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Organization organization = organizationRepository.findById(organizationId).orElseThrow();
        List<JobCreator> jRequest = organization.getJobCreatorRequests();
        List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
        for (JobCreator jobCreator : jRequest)
            profiles.add(modelMapper.map(jobCreator, ProfileDTO.class));
        return profiles;
    }

    Organization save(Organization organization) {
        return organizationRepository.save(organization);
    }

}
