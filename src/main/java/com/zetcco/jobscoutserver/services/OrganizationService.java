package com.zetcco.jobscoutserver.services;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;

import jakarta.transaction.Transactional;

@Service
public class OrganizationService {
    
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProfileDTO> searchOrganizationsByName(String name, int pageCount, int pageSize) {
        if (name.equals(""))
            throw new IllegalArgumentException("Wrong Parameters");
        Pageable page = PageRequest.of(pageCount, pageSize);
        List<Organization> organizations = organizationRepository.findByCompanyNameContainingIgnoreCase(name, page).getContent();
        List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
        for (Organization organization : organizations) 
            profiles.add(userService.getUser(organization.getId()));
        return profiles; 
    }

    public List<ProfileDTO> searchOrganizationsByNameFTS(String name, int pageCount, int pageSize) {
        String keywords = name.replace(' ', '&');
        Pageable page = PageRequest.of(pageCount, pageSize);
        List<Organization> organizations = organizationRepository.findOrganizationByNameFTS(keywords, page).getContent();
        List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
        for (Organization organization : organizations) 
            profiles.add(modelMapper.map(organization, ProfileDTO.class));
        return profiles; 
    }

    @PreAuthorize("hasRole('ORGANIZATION')")
    @Transactional
    public ProfileDTO addJobCreatorToOrganization(Long organizationId, Long jobCreatorId) {
        Organization organization = organizationRepository.findById(organizationId).orElseThrow();
        List<JobCreator> jobCreators = organization.getJobCreators();
        jobCreators.add(jobCreatorRepository.findById(jobCreatorId).orElseThrow());
        for (JobCreator jobCreator : jobCreators) 
            jobCreator.setOrganization(organization);
        organization.setJobCreators(jobCreators);

        jobCreatorRepository.saveAll(jobCreators);
        organizationRepository.save(organization);

        return userService.getUser(organizationId);
    }
}
