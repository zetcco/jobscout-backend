package com.zetcco.jobscoutserver.services.support;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.services.JobSeekerService;
import com.zetcco.jobscoutserver.services.mappers.CategorySkillSetMapper;
import com.zetcco.jobscoutserver.services.mappers.PastExperienceMapper;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;

@Service
public class CVService {

    @Autowired private JobSeekerService jobSeekerService;
    @Autowired private UserMapper userMapper;
    @Autowired private CategorySkillSetMapper categorySkillSetMapper;
    @Autowired private PastExperienceMapper pastExperienceMapper;
    @Autowired private Environment environment;
    
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
                                                    .pastExperiences(pastExperienceMapper.mapToDtos(jobSeeker.getPastExperiences()))
                                                    .intro(jobSeeker.getIntro())
                                                    .categorySkillList(categorySkillSetMapper.mapToDtos(jobSeeker.getCategorySkillSets()))
                                                    .build();
        RestTemplate restTemplate = new RestTemplate();
        String SERVER_URL = environment.getProperty("services.cv_service");
        if (SERVER_URL != null) {
            URI url = new URI(SERVER_URL.concat("/cv/generate"));
            Resource result = restTemplate.postForObject(url, cvDataRequest, Resource.class);
            return result;
        } else {
            throw new AccessDeniedException("CV Service Unavailable");
        }
    } 

    public Object getCVTemplates() throws URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String SERVER_URL = environment.getProperty("services.cv_service");
        if (SERVER_URL != null) {
            URI url = new URI(SERVER_URL.concat("/cv/templates"));
            Object result = restTemplate.getForObject(url, Object.class);
            return result;
        } else {
            throw new AccessDeniedException("CV Service Unavailable");
        }
    }

    public Resource getTemplatePreview(Long templateId) throws RestClientException, URISyntaxException {
        RestTemplate restTemplate = new RestTemplate();
        String SERVER_URL = environment.getProperty("services.cv_service");
        if (SERVER_URL != null) {
            URI url = new URI(SERVER_URL.concat("/cv/templates/") + templateId + "/preview.jpg");
            Resource result = restTemplate.getForObject(url, Resource.class);
            return result;
        } else {
            throw new AccessDeniedException("CV Service Unavailable");
        }
    } 
}
