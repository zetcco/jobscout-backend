package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;

@SpringBootTest
public class JobPostRepositoryTest {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Test
    public void testSaveJobPost() throws Exception{
        Date date =  new Date(); 
        JobPost jobPost = JobPost.builder()
                                .timestamp(date)
                                .dueDate(date)
                                .title("NodeJS Developer")
                                .description("full time job with amazing salary")
                                .type(JobPostType.TYPE_FREELANCE)
                                .urgent(true)
                                .status(JobPostStatus.STATUS_HOLD)
                                .build();
        jobPostRepository.save(jobPost);
    }
    
    @Test
    public void testGetJobPostById(){
        JobPost jobPost = jobPostRepository.findById(1L).orElseThrow();
        System.out.println((jobPost.getTitle()));
    }

    @Test
    public void getGetAllJobPosts(){
        List<JobPost> jobPost = jobPostRepository.findAll();
        jobPost.forEach((p)->{
            System.out.println(p.getTitle());
        });
    }

    @Test
    public void testJobPostByName(){
        List<JobPost> jobPost = jobPostRepository.findByTitleContainingIgnoreCase("developer");
        jobPost.forEach((p)->{
            System.out.println(p.getTitle());
        });
    }

    @Test
    public void testDeleteJobPostById(){
        jobPostRepository.deleteById(2L);
    }

    @Test
    public void testFindByType(){
        Optional<List<JobPost>> jobPost = jobPostRepository.findByType(JobPostType.TYPE_FREELANCE);
        jobPost.ifPresent(list -> list.forEach((s) -> {System.out.println(s.getTitle());}));
    }

    @Test
    public void testFindByStatus(){
        Optional<List<JobPost>> jobPost = jobPostRepository.findByStatus(JobPostStatus.STATUS_HOLD);
        jobPost.ifPresent(list -> list.forEach((s) -> {System.out.println(s.getTitle());}));
    }
    
}
