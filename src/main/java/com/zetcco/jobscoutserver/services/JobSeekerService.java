package com.zetcco.jobscoutserver.services;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.repositories.CategoryRepository;
import com.zetcco.jobscoutserver.repositories.JobSeekerRepository;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.CVDataRequest;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class JobSeekerService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SkillService skillService;

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    public List<Skill> updateCategoryAndSkillListById(Long categortId, List<Long> skillId) throws NotFoundException {
        Category categoryObj = categoryRepository.findById(categortId).orElseThrow();
        List<Skill> skillObj = new ArrayList<>();
        for (Long id : skillId) {
            skillObj.add(skillService.getSkillsById(id));
        }
        JobSeeker jobSeekerObj = jobSeekerRepository.findById(userService.getUser().getId()).orElseThrow();
        jobSeekerObj.setCategory(categoryObj);
        jobSeekerObj.setSkills(skillObj);
        jobSeekerRepository.save(jobSeekerObj);
        return skillObj;
    }

    public Resource generateCV(Long profileId, Long templateId) throws RestClientException, URISyntaxException {
        JobSeeker jobSeeker = jobSeekerRepository.findById(profileId).orElseThrow();
        CVDataRequest cvDataRequest = CVDataRequest.builder()
                                                    .id(profileId)
                                                    .templateId(templateId)
                                                    .firstName(jobSeeker.getFirstName())
                                                    .lastName(jobSeeker.getLastName())
                                                    .address(jobSeeker.getAddress().toString())
                                                    .email(jobSeeker.getEmail())
                                                    .contact(jobSeeker.getContact())
                                                    .photo(userMapper.getProfilePictureLink(jobSeeker))
                                                    .educationalQualifications(null)
                                                    .pastExperience(null)
                                                    .intro("Test intro")
                                                    .skills(jobSeeker.getSkills())
                                                    .build();
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8081/cv/generate");
        Resource result = restTemplate.postForObject(url, cvDataRequest, Resource.class);
        return result;
    } 
}
