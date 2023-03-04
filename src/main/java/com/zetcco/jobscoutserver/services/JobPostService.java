package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.repositories.JobPostRepository;
import com.zetcco.jobscoutserver.services.mappers.JobPostMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

@Service
public class JobPostService {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobPostMapper mapper;

    public JobPostDTO addNewJobPost(JobPostDTO jobPostDTO) throws NotFoundException{
        JobPost jobPost = mapper.mapToEntity(jobPostDTO);
        return this.mapper.mapToDto(jobPostRepository.save(jobPost));
    }

    public JobPostDTO updateJobPost(JobPostDTO jobPostDTO) throws NotFoundException{
        JobPost exsistingjobPost = mapper.mapToEntity(jobPostDTO);
        if(!jobPostRepository.existsById(exsistingjobPost.getId()))
                throw new NotFoundException("Job Post Not Found! - jobPostId : " + exsistingjobPost.getId());
            return this.mapper.mapToDto(jobPostRepository.save(exsistingjobPost));
    }

    public List<JobPostDTO> getAllJobPosts() throws NotFoundException{
        return this.mapper.mapToDtos(jobPostRepository.findAll());
    }

    public JobPostDTO getJobPostById(Long jobPostId) throws NotFoundException{
        JobPost jobPost = jobPostRepository.findById(jobPostId)
        .orElseThrow(()-> new NotFoundException("Such a job post not found! - jobPostId : " + jobPostId));
            return this.mapper.mapToDto(jobPost);
    }

    public List<JobPostDTO> getJobPostByType(JobPostType type) throws NotFoundException{
        List<JobPost> jobPost =  jobPostRepository.findByType(type)
        .orElseThrow(()-> new NotFoundException("such a job post not found!  - Type : " + type));
            return this.mapper.mapToDtos(jobPost);
    }

    public List<JobPostDTO> getJobPostByStatus(JobPostStatus status) throws NotFoundException{
        List<JobPost> jobPost = jobPostRepository.findByStatus(status)
        .orElseThrow(()-> new NotFoundException("such a job post not found! - status : " + status));
        return this.mapper.mapToDtos(jobPost);
    }

    public void deleteJobPostById(Long Id) throws NotFoundException{
        if(!jobPostRepository.existsById(Id))
                throw new NotFoundException("Job Post Not Found!" + Id);
            jobPostRepository.deleteById(Id);
    }
}
