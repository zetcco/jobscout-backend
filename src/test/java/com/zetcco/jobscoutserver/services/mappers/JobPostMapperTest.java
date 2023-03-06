package com.zetcco.jobscoutserver.services.mappers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.JobPost;
import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.repositories.JobCreatorRepository;

@SpringBootTest
public class JobPostMapperTest {
    
    @Autowired
    JobCreatorRepository jobCreatorRepository;

    @Autowired
    JobPostMapper mapper;

//     @Test
//     void testMapToDtoTest() {
//         JobCreator jobCreator = jobCreatorRepository.findById(1L).orElseThrow(); 
//         JobPost jobpost = JobPost.builder().jobCreator(jobCreator).description("Test").build();
//         System.out.println(mapper.mapToDtoTest(jobpost));
//     }
// 
}
