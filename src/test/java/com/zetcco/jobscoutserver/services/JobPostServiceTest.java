package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.repositories.JobPostRepository;
import com.zetcco.jobscoutserver.services.mappers.JobPostMapper;

@SpringBootTest
public class JobPostServiceTest {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobPostMapper mapper;

    // @Autowired
    // JobPostMapper mapper;

    @Test
    void testAddNewJobPost(){
        Date newDate = new Date();
        JobPost jobPost = JobPost.builder()
                                .timestamp(newDate)
                                .dueDate(newDate)
                                .title("Fullstack developer")
                                .description("part time job which you can work from anywhere")
                                .type(JobPostType.TYPE_PART_TIME)
                                .urgent(true)
                                .status(JobPostStatus.STATUS_ACTIVE)
                                .build();
        jobPostService.addNewJobPost(this.mapper.mapToDto(jobPost));
    }

    @Test
    void testUpdateJobPost(){
        List<JobPostDTO> jobPost_1 = jobPostService.getAllJobPosts();
        jobPost_1.forEach((p)->{System.out.println(p.getTitle());});

        JobPostDTO exsistingJobPost = jobPostService.getJobPostById(4L);
        exsistingJobPost.setTitle("Updated Title");
        jobPostService.updateJobPost(exsistingJobPost);

        List<JobPostDTO> jobPost_2 = jobPostService.getAllJobPosts();
        jobPost_2.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByType(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostByType(JobPostType.TYPE_PART_TIME);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByStatus(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostByStatus(JobPostStatus.STATUS_ACTIVE);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }
  
     @Test
      void testDeleteJobPostById(){
        JobPostDTO jobPostDTO = jobPostService.getJobPostById(3L);
        jobPostService.deleteJobPostById(3L , jobPostDTO);
      }
 }
