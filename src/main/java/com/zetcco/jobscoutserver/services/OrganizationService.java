package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.repositories.OrganizationRepository;

@Service
public class OrganizationService {
    
    @Autowired
    private OrganizationRepository organizationRepository;

    public List<Organization> searchOrganizationsByName(String name, int pageCount, int pageSize) {
        String keywords = name.replace(' ', '&');
        Pageable page = PageRequest.of(pageCount, pageSize);
        return organizationRepository.findOrganizationByNameFTS(keywords, page).getContent();
    }
}
