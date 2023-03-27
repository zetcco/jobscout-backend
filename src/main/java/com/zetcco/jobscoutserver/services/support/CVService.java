package com.zetcco.jobscoutserver.services.support;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.mappers.CategorySkillSetMapper;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;

@Service
public class CVService {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategorySkillSetMapper categorySkillSetMapper;
    
    public Resource generateCV(Long profileId, Long templateId) throws RestClientException, URISyntaxException {
        JobSeeker jobSeeker = jobSeekerService.getJobSeeker(profileId);
        CVDataRequest cvDataRequest = CVDataRequest.builder()
                                                    .id(profileId)
                                                    .templateId(templateId)
                                                    .firstName(jobSeeker.getFirstName())
                                                    .lastName(jobSeeker.getLastName())
                                                    .address(jobSeeker.getAddress().toString())
                                                    .email(jobSeeker.getEmail())
                                                    .contact(jobSeeker.getContact())
                                                    .photo(userMapper.getProfilePictureLink(jobSeeker))
                                                    .educationalQualifications(jobSeeker.getQualifications())
                                                    .pastExperiences(jobSeeker.getPastExperiences())
                                                    .intro(jobSeeker.getIntro())
                                                    .categorySkillList(categorySkillSetMapper.mapToDtos(jobSeeker.getCategorySkillSets()))
                                                    .build();
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8081/cv/generate");
        Resource result = restTemplate.postForObject(url, cvDataRequest, Resource.class);
        return result;
    } 

    public Object getCVTemplates() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8081/cv/templates");
        Object result = restTemplate.getForObject(url, Object.class);
        return result;
    }

    public Resource getTemplatePreview(Long templateId) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        URI url = new URI("http://localhost:8081/cv/templates/" + templateId + "/preview.jpg");
        Resource result = restTemplate.getForObject(url, Resource.class);
        return result;
    } 
}
