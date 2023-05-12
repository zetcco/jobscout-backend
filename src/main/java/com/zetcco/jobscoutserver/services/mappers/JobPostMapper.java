package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

@Component
public class JobPostMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

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
        ProfileDTO jobCreator = userMapper.mapToDto(jobPost.getJobCreator());
        ProfileDTO organization = jobPost.getOrganization() != null ? userMapper.mapToDto(jobPost.getOrganization()) : null;
        Long questionaryId = jobPost.getQuestionary() != null ? jobPost.getQuestionary().getId() : null;
        JobPostDTO jobPostDTO = JobPostDTO.builder()
                                            .Id(jobPost.getId())
                                            .category(categoryMapper.mapToDto(jobPost.getCategory()))
                                            .skillList(jobPost.getSkillList())
                                            .title(jobPost.getTitle())
                                            .description(jobPost.getDescription())
                                            .dueDate(jobPost.getDueDate())
                                            .timestamp(jobPost.getTimestamp())
                                            .type(jobPost.getType())
                                            .status(jobPost.getStatus())
                                            .jobCreator(jobCreator)
                                            .organization(organization)
                                            .urgent(jobPost.getUrgent())
                                            .questionaryId(questionaryId)
                                            .applicationCount(jobPost.getApplications().size())
                                            .build();
        return jobPostDTO;
    }
}
