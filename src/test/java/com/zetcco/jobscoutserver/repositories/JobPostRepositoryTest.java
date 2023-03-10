package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.Organization;
import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.services.UserService;

@SpringBootTest
public class JobPostRepositoryTest {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserService userService;

    @Test
    public void testSaveJobPost() throws Exception{

        JobCreator jobCreator = jobCreatorRepository.findById(4L).orElseThrow();
        Category category = categoryRepository.findById(5L).orElseThrow();
        Organization organization = organizationRepository.findById(5L).orElseThrow();

        Date date =  new Date(); 
        JobPost jobPost = JobPost.builder()
                                .jobCreator(jobCreator)
                                .category(category)
                                .organization(organization)
                                .timestamp(date)
                                .dueDate(date)
                                .title("Full Stack Developer")
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

    @Test
    void testGetPostsCustom() {
        Pageable page = PageRequest.of(0, 3);
        List<JobPost> jobPosts = jobPostRepository.getAll(page).getContent();
        // List<JobPost> jobPosts = jobPostRepository.findAll();
        for (JobPost jobPost : jobPosts) {
            if (jobPost.getJobCreator() != null)
                System.out.println(userService.getUser(jobPost.getJobCreator().getId()));
        }
    }

    @Test
    void testGetJobPostByJobCreatorId(){
        List<JobPost> jobPosts = jobPostRepository.findAll();
        for(JobPost jobPost : jobPosts){
            if(jobPost.getJobCreator().getId() == 1L){
                System.out.println(jobPost.getTitle());
            }
        }
    }

    @Test
    void testGetJobPostByOrganizationId(){
        List<JobPost> jobPosts = jobPostRepository.findAll();
        for(JobPost jobPost : jobPosts){
            if(jobPost.getOrganization().getId() == 5L){
                System.out.println(jobPost.getTitle());
            }
        }
    }

    @Test
    void testGetJobPostByCategoryId(){
        List<JobPost> jobPosts = jobPostRepository.findAll();
        for(JobPost jobPost : jobPosts){
            if(jobPost.getCategory().getId() == 5L){
                System.out.println(jobPost.getTitle());
            }
        }
    }

    @Test
    void testGetJobPostNameByFTS() {
        Pageable page = PageRequest.of(0, 3);
        List<JobPost> posts = jobPostRepository.findJobPostByNameFTS("NodeJS&Developer", page).getContent();
        for (JobPost jobpost : posts) System.out.println(jobpost.getTitle());

        System.out.println("----------------");

        Pageable page2 = PageRequest.of(1, 3);
        posts = jobPostRepository.findJobPostByNameFTS("NodeJS", page2).getContent();
        for (JobPost jobpost : posts) System.out.println(jobpost.getTitle());
    }


}
