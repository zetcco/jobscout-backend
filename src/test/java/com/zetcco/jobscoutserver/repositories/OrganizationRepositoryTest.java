package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.Address;

@SpringBootTest
public class OrganizationRepositoryTest {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Test
    public void saveNewOrganization() {
        Address address = Address.builder()
                                .number("41")
                                .street("Street")
                                .town("Town")
                                .city("City")
                                .province("Province")
                                .country("Country")
                                .build();
        Organization organization = new Organization("org@gmail.com", "orgpasswd", address);
        organizationRepository.save(organization);
    }

    @Test
    public void saveOrganizationProfile() {
        Organization organization = organizationRepository.findById(3L).orElseThrow();
        organization.setCompanyName("Creative Software");
        organization.setBusinessRegistration("https://..../...");
        organization.setDisplayPicture("New dp bro");
        organizationRepository.save(organization);
    }

    @Test
    public void setJobCreator() {
        Organization organization = organizationRepository.findById(1L).orElseThrow();
        List<JobCreator> jobCreators = organization.getJobCreators();
        jobCreators.add(jobCreatorRepository.findById(73L).orElseThrow());
        for (JobCreator jobCreator : jobCreators) 
            jobCreator.setOrganization(organization);
        jobCreatorRepository.saveAll(jobCreators);
        organization.setJobCreators(jobCreators);
        organizationRepository.save(organization);
    }

    @Test
    public void getOrganizationById() {
        Pageable firstPage = PageRequest.of(0, 1);
        List<Organization> orgs = organizationRepository.findAll(firstPage).getContent();
        System.out.println(orgs);
    }

    @Test
    public void getOrganizationNameByFTS() {
        Pageable page = PageRequest.of(0, 1);
        List<Organization> orgs = organizationRepository.findOrganizationByNameFTS("creative", page).getContent();
        for (Organization organization : orgs) System.out.println(organization.getCompanyName());

        System.out.println("----------------");

        Pageable page2 = PageRequest.of(1, 1);
        orgs = organizationRepository.findOrganizationByNameFTS("creative", page2).getContent();
        for (Organization organization : orgs) System.out.println(organization.getCompanyName());
    }

    @Test
    public void getOrganizationByName() {
        Pageable page = PageRequest.of(0, 2);
        List<Organization> orgs = organizationRepository.findByCompanyNameContainingIgnoreCase("c", page).getContent();
        for (Organization organization : orgs) System.out.println(organization.getCompanyName());

        System.out.println("----------------");

        Pageable page2 = PageRequest.of(1, 2);
        orgs = organizationRepository.findByCompanyNameContainingIgnoreCase("c", page2).getContent();
        for (Organization organization : orgs) System.out.println(organization.getCompanyName());
    }
}
