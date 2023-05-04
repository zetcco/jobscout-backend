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
import com.zetcco.jobscoutserver.services.mappers.JobPostMapper;

import jakarta.transaction.Transactional;

@SpringBootTest
public class JobPostServiceTest {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobPostMapper mapper;

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
        List<JobPostDTO> jobPost_1 = jobPostService.getAllJobPosts(0, 2);
        jobPost_1.forEach((p)->{System.out.println(p.getTitle());});

        JobPostDTO exsistingJobPost = jobPostService.getJobPostById(4L);
        exsistingJobPost.setTitle("Updated Title");
        jobPostService.updateJobPost(exsistingJobPost);

        List<JobPostDTO> jobPost_2 = jobPostService.getAllJobPosts(1, 2);
        jobPost_2.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByType(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostByType(JobPostType.TYPE_PART_TIME);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindAllJobPosts(){
        List<JobPostDTO> jobPosts = jobPostService.getAllJobPosts(0 , 10);
        jobPosts.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByStatus(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostByStatus(JobPostStatus.STATUS_ACTIVE);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByJobCreatorId(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostsByJobCreatorId(4L);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByOrganizationId(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostsByOrganizationId(5L);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }

    @Test
    void testFindJobPostByCategoryId(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostsByCategoryId(5L);
        jobPost.forEach((p)->{System.out.println(p.getTitle());});
    }
  
     @Test
     @Transactional
      void testDeleteJobPostById(){
        jobPostService.deleteJobPostById(4L);
      }

      @Test
      void testSearchJobPostsByNameFTS(){
        List<JobPostDTO> jobPost = jobPostService.getJobPostByNameFTS("Senior", 0, 1);
        jobPost.forEach((p)->{System.out.println(p.getDescription());});
      }

      @Test
      void testGetJobPostCountByJobCreatorId(){
        System.out.println(jobPostService.getJobPostsByJobCreatorId(4L).size());
      }
 }
