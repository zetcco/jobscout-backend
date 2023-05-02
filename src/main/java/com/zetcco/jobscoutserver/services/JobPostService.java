package com.zetcco.jobscoutserver.services;

import java.util.List;
import java.util.Date;
import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.Role;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.repositories.JobPostRepository;
import com.zetcco.jobscoutserver.services.mappers.JobPostMapper;
import com.zetcco.jobscoutserver.services.support.NotFoundException;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;

import jakarta.transaction.Transactional;

@Service
public class JobPostService {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobPostMapper mapper;

    @Autowired
    private JobCreatorService jobCreatorService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    @Transactional
    public JobPostDTO addNewJobPost(JobPostDTO jobPostDTO) throws NotFoundException{
        JobPost jobPost = mapper.mapToEntity(jobPostDTO);
        JobCreator jobCreator = jobCreatorService.getJobCreatorById(userService.getUser().getId());
        Organization organization = jobCreator.getOrganization();
        jobPost.setJobCreator(jobCreator);
        jobPost.setOrganization(organization);
        jobPost.setTimestamp(new Date());
        JobPost newJobPost = jobPostRepository.save(jobPost);

        if (organization != null) {
            List<JobPost> orgPosts = organization.getJobPost();
            orgPosts.add(newJobPost);
            organization.setJobPost(orgPosts);
            organizationService.save(organization);
        }

        List<JobPost> jobPosts = jobCreator.getJobPost();
        jobPosts.add(newJobPost);
        jobCreator.setJobPost(jobPosts);
        jobCreatorService.save(jobCreator);

        return this.mapper.mapToDto(newJobPost);
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

    public List<JobPostDTO> getJobPostByCategoryIdAndSkillIdAndStatusAndType(Long categoryId , Long skillId , JobPostStatus status , JobPostType type) throws NotFoundException{
        List<JobPost> jobPosts = new ArrayList<JobPost>();
        for(JobPost jobPost : jobPostRepository.findAll()){
            if(jobPost.getCategory().getId() == categoryId || jobPost.getStatus() == status || jobPost.getType() == type)
                jobPosts.add(jobPost);
        }
        if(jobPosts.isEmpty() == true)
                throw new NotFoundException("Such a job post not found!");
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
        JobPost jobPost = jobPostRepository.findById(Id).orElseThrow();
        JobCreator jobCreator = jobPost.getJobCreator();
        Organization organization = jobPost.getOrganization();
        if (organization != null) {
            List<JobPost> orgJobPosts = organization.getJobPost();
            orgJobPosts.remove(jobPost);
            organization.setJobPost(orgJobPosts);
            organizationService.save(organization);
        }
        List<JobPost> jobPosts = jobCreator.getJobPost();
        jobPosts.remove(jobPost);
        jobCreator.setJobPost(jobPosts);
        jobCreatorService.save(jobCreator);
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


    public Long getJobPostCount() throws NotFoundException {
        ProfileDTO user = userService.getUser();
        if (user.getRole() == Role.ROLE_JOB_CREATOR)
            return this.getJobPostCountByJobCreatorId(user.getId());
        else if (user.getRole() == Role.ROLE_ORGANIZATION)
            return this.getJobPostCountByOrganizationId(user.getId());
        else
            throw new NotFoundException("User not found");
    }

    private Long getJobPostCountByJobCreatorId(Long id) {
        return jobPostRepository.countByJobCreatorId(id);
    }

    private Long getJobPostCountByOrganizationId(Long id) {
        return jobPostRepository.countByOrganizationId(id);
    }


    public Long getActivatedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        if (user.getRole() == Role.ROLE_JOB_CREATOR)
            return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_ACTIVE);
        else if (user.getRole() == Role.ROLE_ORGANIZATION)
            return this.getJobPostCountByOrganizationIdAndStatus(user.getId() , JobPostStatus.STATUS_ACTIVE);
        else
            throw new NotFoundException("User not found");   
    }

    public Long getDeactivatedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        if (user.getRole() == Role.ROLE_JOB_CREATOR)
            return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_OVER);
        else if (user.getRole() == Role.ROLE_ORGANIZATION)
            return this.getJobPostCountByOrganizationIdAndStatus(user.getId() , JobPostStatus.STATUS_OVER);
        else
            throw new NotFoundException("User not found");   
    }

    public Long getHoldedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        if (user.getRole() == Role.ROLE_JOB_CREATOR)
            return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_HOLD);
        else if (user.getRole() == Role.ROLE_ORGANIZATION)
            return this.getJobPostCountByOrganizationIdAndStatus(user.getId() , JobPostStatus.STATUS_HOLD);
        else
            throw new NotFoundException("User not found");   
    }

    private Long getJobPostCountByJobCreatorIdAndStatus(Long id , JobPostStatus status){
        return jobPostRepository.countByJobCreatorIdAndStatus(id, status);
    }

    private Long getJobPostCountByOrganizationIdAndStatus(Long id , JobPostStatus status){
        return jobPostRepository.countByOrganizationIdAndStatus(id, status);
    }

}
