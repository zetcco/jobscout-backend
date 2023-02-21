package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.SkillsRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SkillService {


    @Autowired
    private SkillsRepository skillsRepository;
    // public List<ProfileDTO> searchOrganizationsByName(String name, int pageCount, int pageSize) {
    //     if (name.equals(""))
    //         throw new IllegalArgumentException("Wrong Parameters");
    //     Pageable page = PageRequest.of(pageCount, pageSize);
    //     List<Organization> organizations = organizationRepository.findByCompanyNameContainingIgnoreCase(name, page).getContent();
    //     List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
    //     for (Organization organization : organizations) 
    //         profiles.add(userService.getUser(organization.getId()));
    //     return profiles; 
    // }

    // public List<ProfileDTO> searchOrganizationsByNameFTS(String name, int pageCount, int pageSize) {
    //     String keywords = name.replace(' ', '&');
    //     Pageable page = PageRequest.of(pageCount, pageSize);
    //     List<Organization> organizations = organizationRepository.findOrganizationByNameFTS(keywords, page).getContent();
    //     List<ProfileDTO> profiles = new LinkedList<ProfileDTO>();
    //     for (Organization organization : organizations) 
    //         profiles.add(modelMapper.map(organization, ProfileDTO.class));  
    //     return profiles; 
    // }

    public List<Skill> searchSkillByName(String name, int pageCount, int pageSize) {
        if(name.equals(""))
            throw new IllegalArgumentException("Wrong Parameters");
        // Pageable page = PageRequest.of(pageCount, pageSize);
        List<Skill> skills = skillsRepository.findByNameContainingIgnoreCase(name);
        return null;
    }

    @Transactional
    List<Skill> addSkills(Long skillId) throws NotFoundException, DataIntegrityViolationException {
        Skill skill = this.getSkillsById(skillId);

        return null;
    }

    Skill getSkillsById(Long skillId) throws NotFoundException {
        return skillsRepository.findById(skillId).orElseThrow(() -> new NotFoundException());
    }
}
