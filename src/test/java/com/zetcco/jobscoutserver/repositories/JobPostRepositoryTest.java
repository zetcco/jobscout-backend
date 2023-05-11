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

import jakarta.transaction.Transactional;

@SpringBootTest
public class JobPostRepositoryTest {

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Test
    public void testSaveJobPost() throws Exception{

        JobCreator jobCreator = jobCreatorRepository.findById(4L).orElseThrow();
        Category category = categoryRepository.findById(3L).orElseThrow();
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
    void testGetJobPostByJobCreatorId(){
        List<JobPost> jobPosts = jobPostRepository.findAll();
        for(JobPost jobPost : jobPosts){
            if(jobPost.getJobCreator().getId() == 4L){
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
        Pageable page = PageRequest.of(0, 4);
        List<JobPost> posts = jobPostRepository.findJobPostByNameFTS("commercial&bank", page).getContent();
        for (JobPost jobpost : posts) System.out.println(jobpost.getDescription());

        System.out.println("----------------");

        Pageable page2 = PageRequest.of(1, 4);
        posts = jobPostRepository.findJobPostByNameFTS("commercial&bank", page2).getContent();
        for (JobPost jobpost : posts) System.out.println(jobpost.getDescription());
    }

    @Transactional
    @Test
    void testGetJobPostCount() {
        JobCreator jobCreator = jobCreatorRepository.findById(4L).orElseThrow();
        List<JobPost> jobPosts = jobCreator.getJobPost();
        System.out.println(jobPosts);
        System.out.println(jobPostRepository.countByJobCreatorId(4L));
    }

    @Test
    void testGetActivatedJobPostCountByUserId(){
        System.out.println(jobPostRepository.countByJobCreatorIdAndStatus(14L, JobPostStatus.STATUS_ACTIVE));
    }

    @Test
    void testSaveMultipleJobPosts(){
        JobCreator jobCreator = jobCreatorRepository.findById(4L).orElseThrow();
        Category category = categoryRepository.findById(5L).orElseThrow();
        Organization organization = organizationRepository.findById(5L).orElseThrow();
        Date date =  new Date(); 

        List<JobPost> jobPosts = List.of(
            new JobPost(
                        null, date , 
                        date , 
                        "Senior Network Engineer" , 
                        "We are Sri Lanka's premier private sector commercial bank. Our visionary journey has taken us beyond the realms of business as we have made a conscious effort to go where no bank has dared to go; from downtrodden villages long-forgotten, to the world across the shores. The driving force behind this epoch-making journey is our strong team of achievers, affectionately known as the Hatna Family. As we continue to make history and move ahead, we invite dynamic and ambitious individuals to join us in our trailblazing banking saga" ,
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE ,
                        null, 
                        null,
                        jobCreator , 
                        category , 
                        organization
                        ), 

            new JobPost(
                        null, date , 
                        date , 
                        "Senior System Engineer" , 
                        "Onsys Technologies is looking for talented and experienced Senior Systems Engineers to join our Global IT Services Delivery team. This is an excellent opportunity to work on cutting- edge technologies including Amazon AWS, Data Centre, Virtualization etc. to service our clients in Sri Lanka, Australia, and the Middle East.At Onsys, we create a learning culture, developing people to deliver excellent results to our clients worldwide. We have multiple opportunities for Systems Engineers to join the Global IT Services delivery Team based in Sri Lanka." , 
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator , 
                        category , 
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Tech Lead - Java" , 
                        "At Epic, not only will you be working for a company with the best and most nationally recognized software solutions, but you will become a domain expert in Fintech as well. We do our own R&D and we have our own IPs. Sound interesting? Then join an elite team of professionals at Epic and start ascending your career." , 
                        JobPostType.TYPE_PERMANENT , 
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "System Engineer (Linux)" ,
                        "Established in 1992 by the Blue Chip Group (UK), Blue Chip Technical Services (Pvt) Ltd is one of several companies at the forefront of Sri Lanka’s Information Technology and Services sector, equipped with the professional expertise to deliver the highest level of customer service. In line with our diverse and growing customer portfolio we are looking for individuals to become part of the Blue Chip team.",
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ), 

            new JobPost(
                        null, date , 
                        date , 
                        "Associate WMS (Warehouse Management System) Consultant" ,
                        "ITX360 (Pvt) Ltd, subsidiary of Expolanka Holdings PLC is a technology solutions company focused on providing both local and international markets with 360° innovative enterprise solutions and services in automation, Cloud, ERP, Infrastructure, Software development, Information security and managed IT across all industries for any scale of business. We are currently in the process of expanding our footprint globally across all sectors and we are on the lookout for dynamic individuals to join our team",
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Software Engineer" ,
                        "The Company is presently seeking suitable applicants to take up the position of Full Stack Software Developer." ,
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Assistant Manager and IT Operations / IT Executive" ,
                        "Emerging Media was inaugurated in 2009. We were formed with the idea of introducing a revolutionizing concept in the field of advertising in Sri Lanka. Emerging Media is the market leader in location based indoor advertising & outdoor advertising with number of prime locations in Sri Lanka." ,
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Firewall Administrator / Security Engineer (Executive Grade)" ,
                        "We are Sri Lanka's premier private sector commercial bank. Our visionary journey has taken us beyond the realms of business as we have made a conscious effort to go where no bank has dared to go; from downtrodden villages long-forgotten, to the world across the shores. The driving force behind this epoch-making journey is our strong team of achievers, affectionately known as the Hatna Family. As we continue to make history and move ahead, we invite dynamic and ambitious individuals to join us in our trailblazing banking saga." ,
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Machine Learning Engineer" ,
                        "We are looking for bright minds to help us create a world of happy experiences.We are in search of a Machine Learning Engineer who can create machine learning models and retraining systems through exceptional skills in statistics and programming whilst building efficient self-learning applications by the use of extensive knowledge of data science and software engineering." ,
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        ),

            new JobPost(
                        null, date , 
                        date , 
                        "Data Engineer" ,
                        "Zuse Technologies (Pvt) Ltd, is a technology service provider and product development company operating primarily in the education technology domain. We are on a mission to optimize the learning experience of students using technology and enable seamless collaboration between students, teachers, parents and school administration. Currently we are handling implementation and integration of multiple large-scale user platforms and the development of mobile and web-based solutions on behalf of our clients." , 
                        JobPostType.TYPE_PERMANENT ,
                        true , 
                        JobPostStatus.STATUS_ACTIVE , 
                        null,
                        null, jobCreator,
                        category,
                        organization
                        )
            );
            jobPostRepository.saveAll(jobPosts);
    }


}
