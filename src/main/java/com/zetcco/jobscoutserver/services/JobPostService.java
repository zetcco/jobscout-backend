package com.zetcco.jobscoutserver.services;

import java.util.List;
import java.util.Date;
import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.zetcco.jobscoutserver.domain.JobApplication;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.Notification;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.questionary.Questionary;
import com.zetcco.jobscoutserver.domain.questionary.QuestionaryAttempt;
import com.zetcco.jobscoutserver.domain.support.ApplicationStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.MeetingDTO;
import com.zetcco.jobscoutserver.domain.support.Notification.NotificationType;
import com.zetcco.jobscoutserver.domain.support.dto.JobApplicationDTO;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.repositories.JobApplicationRepository;
import com.zetcco.jobscoutserver.repositories.JobPostRepository;
import com.zetcco.jobscoutserver.services.mappers.JobApplicationMapper;
import com.zetcco.jobscoutserver.services.mappers.JobPostMapper;
import com.zetcco.jobscoutserver.services.questionary.QuestionaryService;
import com.zetcco.jobscoutserver.services.support.ConversationDTO;
import com.zetcco.jobscoutserver.services.support.JobPostForm;
import com.zetcco.jobscoutserver.services.support.MessageDTO;
import com.zetcco.jobscoutserver.services.support.ProfileDTO;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

import jakarta.transaction.Transactional;

@Service
public class JobPostService {

    @Autowired private JobPostRepository jobPostRepository;
    @Autowired private JobPostMapper mapper;
    @Autowired private JobCreatorService jobCreatorService;
    @Autowired private UserService userService;
    @Autowired private OrganizationService organizationService;
    @Autowired private QuestionaryService questionaryService;
    @Autowired private JobSeekerService jobSeekerService;
    @Autowired private JobApplicationRepository jobApplicationRepository;
    @Autowired private JobApplicationMapper jobApplicationMapper;
    @Autowired private NotificationService notificationService;
    @Autowired private MeetingService meetingService;
    @Autowired private ConversationService conversationService;
    @Autowired private MessageService messageService;

    @Transactional
    public JobPostDTO addNewJobPost(JobPostForm jobPostForm) throws NotFoundException{
        JobPost jobPost = mapper.mapToEntity(jobPostForm.getJobPost());
        if (jobPostForm.getQuestionary() != null) {
            Questionary questionary = questionaryService.createQuestionary(jobPostForm.getQuestionary());
            jobPost.setQuestionary(questionary);
        }
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
        List<JobPost> jobPosts = jobPostRepository.findAll(pageable).toList();
        if(jobPosts.isEmpty() == true){
            throw new NotFoundException("Such job posts not found!");
        }
        return this.mapper.mapToDtos(jobPosts);
    }

    public JobPost findJobPostById(Long jobPostId) throws NotFoundException {
        return jobPostRepository.findById(jobPostId)
        .orElseThrow(()-> new NotFoundException("Such a job post not found! - jobPostId : " + jobPostId));
    }

    public JobPostDTO getJobPostById(Long jobPostId) throws NotFoundException{
        return this.mapper.mapToDto(this.findJobPostById(jobPostId));
    }

    private List<JobPost> findJobPostsByJobCreatorId(Long jobCreatorId)  throws NotFoundException {
        List<JobPost> jobPosts = new ArrayList<JobPost>();
        for(JobPost jobPost : jobPostRepository.findAll()){
            if(jobPost.getJobCreator().getId() == jobCreatorId)
                jobPosts.add(jobPost);        
        }
        if(jobPosts.isEmpty() == true)
            throw new NotFoundException("Such a job post not found! - job creator id: " + jobCreatorId);
        return jobPosts;
    }

    public List<JobPostDTO> getJobPostsByJobCreatorId(Long jobCreatorId) throws NotFoundException{
        return this.mapper.mapToDtos(this.findJobPostsByJobCreatorId(jobCreatorId));
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
        return this.getJobPostCountByJobCreatorId(user.getId());
    }

    private Long getJobPostCountByJobCreatorId(Long id) {
        return jobPostRepository.countByJobCreatorId(id);
    }

    public Long getActivatedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_ACTIVE);
    }

    public Long getDeactivatedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_OVER);
    }

    public Long getHoldedJobPostCount() throws NotFoundException{
        ProfileDTO user = userService.getUser();
        return this.getJobPostCountByJobCreatorIdAndStatus(user.getId() , JobPostStatus.STATUS_HOLD);
    }

    private Long getJobPostCountByJobCreatorIdAndStatus(Long id , JobPostStatus status){
        return jobPostRepository.countByJobCreatorIdAndStatus(id, status);
    }

    public List<JobPostDTO> searchForJobPost(Specification<JobPost> jobPostSpec, Pageable pageable) {
        return mapper.mapToDtos(jobPostRepository.findAll(jobPostSpec, pageable).toList());
    }

    public JobApplicationDTO applyForJobPost(Long jobPostId) throws AccessDeniedException, NotFoundException {
        Long jobSeekerId = userService.getAuthUser().getId();
        return this.applyForJobPost(jobPostId, jobSeekerId);
    }

    public JobApplicationDTO applyForJobPost(Long jobPostId, Long jobSeekerId) throws AccessDeniedException {
        JobPost jobPost = this.findJobPostById(jobPostId);
        JobSeeker jobSeeker = jobSeekerService.getJobSeeker(jobSeekerId);
        if (jobPost.getApplications().stream().map(application -> application.getJobSeeker()).toList().contains(jobSeeker))
            throw new AccessDeniedException("Cannot re-submit");

        JobApplication jobApplication = JobApplication.builder().jobSeeker(jobSeeker).jobPost(jobPost).status(ApplicationStatus.APPLIED).build();
        if (jobPost.getQuestionary() != null) {
            Questionary questionary = jobPost.getQuestionary();
            QuestionaryAttempt attempt = questionaryService.getAttemptByJobSeekerIdAndQuestionaryId(jobSeekerId, questionary.getId());
            if (Float.compare(attempt.getScore(), 70) < 0) 
                throw new AccessDeniedException("Insufficient marks. Cannot apply");
        }

        jobApplication = this.jobApplicationRepository.save(jobApplication);
        jobPost.getApplications().add(jobApplication);

        jobPostRepository.save(jobPost);

        return jobApplicationMapper.mapToDto(jobApplication);
        
    }

    public List<JobApplicationDTO> getApplications(Long jobPostId) throws AccessDeniedException {
        Long jobCreatorId = userService.getAuthUser().getId();
        return this.getApplications(jobPostId, jobCreatorId);
    }

    public List<JobApplicationDTO> getApplications(Long jobPostId, Long jobCreatorId) throws AccessDeniedException {
        JobPost jobPost = this.findJobPostById(jobPostId);
        if (jobPost.getJobCreator().getId() != jobCreatorId)
            throw new AccessDeniedException("You do not have permission");
        return jobApplicationMapper.mapToDtos(jobPost.getApplications());
    }

    private JobApplication getJobApplicationById(Long id) throws NotFoundException {
        JobApplication jobApplication = jobApplicationRepository.findById(id).orElseThrow(() -> new NotFoundException("Job Application not found"));
        return jobApplication;
    }

    public void acceptJobApplication(Long jobApplicationId) throws NotFoundException, AccessDeniedException, JsonProcessingException {
        JobApplication jobApplication = this.getJobApplicationById(jobApplicationId);
        jobApplication.setStatus(ApplicationStatus.ACCEPTED);
        notificationService.sendToUser(new Notification(jobApplication.getJobSeeker(), "You got accepted!", "Job Application got accepted", NotificationType.RECOMMENDATION));
        this.jobApplicationRepository.save(jobApplication);
    }

    public void rejectJobApplication(Long jobApplicationId) throws NotFoundException, AccessDeniedException, JsonProcessingException {
        JobApplication jobApplication = this.getJobApplicationById(jobApplicationId);
        notificationService.sendToUser(new Notification(jobApplication.getJobSeeker(), "Application rejected", "Job Application got rejected", NotificationType.RECOMMENDATION));
        jobApplication.setStatus(ApplicationStatus.REJECTED);
        this.jobApplicationRepository.save(jobApplication);
    }

    public void setJobPostStatus(Long jobPostId, JobPostStatus status) throws NotFoundException, AccessDeniedException {
        JobPost jobPost = this.findJobPostById(jobPostId);
        if (jobPost.getJobCreator().getId().equals(userService.getAuthUser().getId())) {
            jobPost.setStatus(status);
            jobPostRepository.save(jobPost);
        } else {
            throw new AccessDeniedException("You do not have permission to perform this action.");
        }
    }

    public List<JobApplicationDTO> filterJobApplications(Specification<JobApplication> spec) {
        return jobApplicationMapper.mapToDtos(jobApplicationRepository.findAll(spec));
    }

    public List<JobApplication> getAllJobApplications(Long jobSeekerId) {
        return jobApplicationRepository.findByJobSeekerId(jobSeekerId);
    }

    public List<JobApplicationDTO> getAllJobApplications() {
        return jobApplicationMapper.mapToDtos(this.getAllJobApplications(userService.getAuthUser().getId()));
    }

    public void scheduleInterview(Long jobApplicationId, LocalDate timestamp, String time) throws JsonProcessingException, NotFoundException {
        JobApplication application = this.getJobApplicationById(jobApplicationId);
        application.setStatus(ApplicationStatus.INTERVIEW_SELECTED);
        MeetingDTO meetingDTO = meetingService.hostMeeting(new MeetingDTO(null, null, timestamp, null));
        Long jobCreatorId = userService.getAuthUser().getId();
        List<Long> participants = List.of(application.getJobSeeker().getId());
        ConversationDTO conversationDTO = conversationService.createConversation(new ArrayList<>(participants));
        String message = "You have been called for interview for the Application you submitted to,\n\n" + application.getJobPost().getTitle() + "\n\n Please attend to the Interview held on " + timestamp.toString() + " at " + time + ". Please use the link below to attend to the interview.\n\nhttp://localhost:3000/meet/" + meetingDTO.getLink() + "\n\nThank you!";
        MessageDTO messageDTO = new MessageDTO(
            null, 
            jobCreatorId,
            null, 
            message, conversationDTO.getId(), null);
        messageService.sendMessage(conversationDTO.getId(), messageDTO);
        notificationService.sendToUser(new Notification(application.getJobSeeker(), "Calling for Interview", "You have been called for an Interview", NotificationType.RECOMMENDATION));
    }

    public Boolean checkIfApplied(Long jobPostId) {
        JobApplication jobApplication = jobApplicationRepository.findByJobSeekerIdAndJobPostId(userService.getAuthUser().getId(), jobPostId);
        if (jobApplication == null)
            return false;
        else
            return true;
    }


}
