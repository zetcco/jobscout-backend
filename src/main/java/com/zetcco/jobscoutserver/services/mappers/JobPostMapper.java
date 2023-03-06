package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.services.UserService;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

@Component
public class JobPostMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    public List<JobPostDTO> mapToDtos(List<JobPost> jobPosts){
        return jobPosts.stream().map(jobPost -> mapToDto(jobPost)).toList(); 
    } 
    
    public JobPost mapToEntity(JobPostDTO jobPostDTO){
        return this.modelMapper.map(jobPostDTO, JobPost.class);
    }

    public List<JobPost> mapToEntites(List<JobPostDTO> Dtos){
        return Dtos.stream().map(Dto -> mapToEntity(Dto)).toList();
    }

    public JobPostDTO mapToDto(JobPost jobPost) {
        ProfileDTO jobCreator = userService.getUser(jobPost.getJobCreator().getId());
        JobPostDTO jobPostDTO = JobPostDTO.builder()
                                            .Id(jobPost.getId())
                                            .category(jobPost.getCategory())
                                            .title(jobPost.getTitle())
                                            .description(jobPost.getDescription())
                                            .dueDate(jobPost.getDueDate())
                                            .timestamp(jobPost.getTimestamp())
                                            .type(jobPost.getType())
                                            .status(jobPost.getStatus())
                                            .jobCreator(jobCreator)
                                            .urgent(jobPost.getUrgent())
                                            .build();
        return jobPostDTO;
    }
}
