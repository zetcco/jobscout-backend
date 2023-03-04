package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.services.UserService;

@Component
public class JobPostMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    public JobPostDTO mapToDto(JobPost jobPost){
        return this.modelMapper.map(jobPost , JobPostDTO.class);
    }

    public List<JobPostDTO> mapToDtos(List<JobPost> jobPosts){
        return jobPosts.stream().map(jobPost -> mapToDto(jobPost)).toList(); 
    } 
    
    public JobPost mapToEntity(JobPostDTO jobPostDTO){
        return this.modelMapper.map(jobPostDTO, JobPost.class);
    }

    public List<JobPost> mapToEntites(List<JobPostDTO> Dtos){
        return Dtos.stream().map(Dto -> mapToEntity(Dto)).toList();
    }

    public JobPostDTO mapToDtoTest(JobPost jobPost) {
        TypeMap<JobPost, JobPostDTO> propertyMapper = this.modelMapper.createTypeMap(JobPost.class, JobPostDTO.class);
        propertyMapper.addMapping(src -> src.getJobCreator(), (dest, val) -> dest.setProfileDTO( userService.getUser( ((JobCreator)val).getId() ) ) );
        return propertyMapper.map(jobPost);
    }
}
