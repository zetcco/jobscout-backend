package com.zetcco.jobscoutserver.services;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;

import jakarta.transaction.Transactional;

@SpringBootTest
public class JobSeekerServiceTest {

    @Autowired
    private JobSeekerService jobSeekerService;

    @Test
    @Transactional
    void testGetQualificationsById() {
        List<Qualification> qualifications = this.jobSeekerService.getQualificationsById(117L);
        System.out.println(qualifications);
    }
}
