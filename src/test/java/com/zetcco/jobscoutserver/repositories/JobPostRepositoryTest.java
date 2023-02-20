package com.zetcco.jobscoutserver.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.JobPost;

@SpringBootTest
public class JobPostRepositoryTest {
    @Autowired
    private JobPostRepository jobPostRepository;

    @Test
    public void SaveJobPost(){
    }
    
}
