package com.zetcco.jobscoutdemo.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutdemo.domain.JobSeeker;
import com.zetcco.jobscoutdemo.domain.support.Address;

@SpringBootTest
public class JobSeekerRepositoryTest {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Test
    public void saveJobSeeker() {
        Address address = Address.builder()
                                .number("41")
                                .street("Street")
                                .town("Town")
                                .city("City")
                                .province("Province")
                                .country("Country")
                                .build();
        JobSeeker jobSeeker = new JobSeeker("seek@gmail.com", "seekpasswd", address);
        jobSeekerRepository.save(jobSeeker);
    }

    @Test
    public void saveJobSeekerProfile() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(2L).orElseThrow();
        jobSeeker.setFirstName("Indrajith");
        jobSeeker.setLastName("Madhumal");
        jobSeekerRepository.save(jobSeeker);
    }
}
