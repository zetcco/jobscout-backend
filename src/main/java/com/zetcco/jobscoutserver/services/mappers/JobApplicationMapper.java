package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.JobApplication;
import com.zetcco.jobscoutserver.domain.support.dto.JobApplicationDTO;

@Component
public class JobApplicationMapper {

    @Autowired
    private JobPostMapper jobPostMapper;

    @Autowired
    private UserMapper userMapper;

    public JobApplicationDTO mapToDto(JobApplication application) {
        return new JobApplicationDTO(application.getStatus(), userMapper.mapToDto(application.getJobSeeker()), jobPostMapper.mapToDto(application.getJobPost()));
    }

    public List<JobApplicationDTO> mapToDtos(List<JobApplication> applications) {
        return applications.stream().map( application -> this.mapToDto(application) ).toList();
    }
    
}
