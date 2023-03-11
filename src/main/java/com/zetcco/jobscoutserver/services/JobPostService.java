package com.zetcco.jobscoutserver.services;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public List<JobPostDTO> getAllJobPosts(int page, int size) throws NotFoundException{
        Pageable pageable = PageRequest.of(page, size);
        return this.mapper.mapToDtos(jobPostRepository.getAll(pageable).getContent());
    }


    public JobPostDTO getJobPostById(Long jobPostId) throws NotFoundException{
        JobPost jobPost = jobPostRepository.findById(jobPostId)
        .orElseThrow(()-> new NotFoundException("Such a job post not found! - jobPostId : " + jobPostId));
            return this.mapper.mapToDto(jobPost);
    }


    public List<JobPostDTO> getJobPostsByJobCreatorId(Long jobCreatorId) throws NotFoundException{
        List<JobPost> jobPosts = new ArrayList<JobPost>();
        for(JobPost jobPost : jobPostRepository.findAll()){
            if(jobPost.getJobCreator().getId() == jobCreatorId)
                jobPosts.add(jobPost);        
        }
        if(jobPosts.isEmpty() == true)
            throw new NotFoundException("Such a job post not found! - job creator id: " + jobCreatorId);
        return this.mapper.mapToDtos(jobPosts);
    }


    public List<JobPostDTO> getJobPostsByOrganizationId(Long organizationId) throws NotFoundException{
        List<JobPost> jobPosts = new ArrayList<JobPost>();
        for(JobPost jobPost : jobPostRepository.findAll()){
            if(jobPost.getOrganization().getId() == organizationId)
                jobPosts.add(jobPost);            
        }
        if(jobPosts.isEmpty() == true)
            throw new NotFoundException("Such a job post not found! - organization id: " + organizationId);
        return this.mapper.mapToDtos(jobPosts);
    }


    public List<JobPostDTO> getJobPostsByCategoryId(Long categoryId) throws NotFoundException{
        List<JobPost> jobPosts = new ArrayList<JobPost>();
        for(JobPost jobPost : jobPostRepository.findAll()){
            if(jobPost.getCategory().getId() == categoryId)
                jobPosts.add(jobPost);            
        }
        if(jobPosts.isEmpty() == true)
            throw new NotFoundException("Such a job post not found! - category id: " + categoryId);
        return this.mapper.mapToDtos(jobPosts);
    }


    public List<JobPostDTO> getJobPostByType(JobPostType type) throws NotFoundException{
        List<JobPost> jobPost =  jobPostRepository.findByType(type)
        .orElseThrow(()-> new NotFoundException("Such a job post not found!  - type : " + type));
            return this.mapper.mapToDtos(jobPost);
    }


    public List<JobPostDTO> getJobPostByStatus(JobPostStatus status) throws NotFoundException{
        List<JobPost> jobPost = jobPostRepository.findByStatus(status)
        .orElseThrow(()-> new NotFoundException("Such a job post not found! - status : " + status));
        return this.mapper.mapToDtos(jobPost);
    }


    public void deleteJobPostById(Long Id) throws NotFoundException{
        if(!jobPostRepository.existsById(Id))
                throw new NotFoundException("Job Post Not Found!" + Id);
            jobPostRepository.deleteById(Id);
    }

    public List<JobPostDTO> getJobPostByNameFTS(String desc , int pageCount, int pageSize) throws NotFoundException{
    
        String keywords = desc.replace(' ', '&');
        Pageable page = PageRequest.of(pageCount , pageSize);
        List<JobPost> jobPosts = jobPostRepository.findJobPostByNameFTS(keywords, page).getContent();
        List<JobPostDTO> profiles = new LinkedList<JobPostDTO>();
        for (JobPost jobPost : jobPosts) 
            profiles.add(this.mapper.mapToDto(jobPost));
        if(profiles.isEmpty() == true)
            throw new NotFoundException("Job Post Not Found!");
        return profiles;    
    }
}
