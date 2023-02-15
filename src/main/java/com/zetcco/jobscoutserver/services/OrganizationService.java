package com.zetcco.jobscoutserver.services;

import java.util.LinkedList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.controllers.support.ProfileDTO;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;

@Service
public class OrganizationService {
    
    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserService userService;

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
}
