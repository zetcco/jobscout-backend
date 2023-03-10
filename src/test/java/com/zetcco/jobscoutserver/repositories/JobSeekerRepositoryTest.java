package com.zetcco.jobscoutserver.repositories;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.JobSeeker;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.NameTitle;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Degree;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Institute;
import com.zetcco.jobscoutserver.domain.support.EducationalQualification.Qualification;
import com.zetcco.jobscoutserver.repositories.support.Qualification.DegreeRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.InstituteRepository;
import com.zetcco.jobscoutserver.repositories.support.Qualification.QualificationRepository;

@SpringBootTest
public class JobSeekerRepositoryTest {

    @Autowired
    private JobSeekerRepository jobSeekerRepository;

    @Autowired
    private DegreeRepository degreeRepository;

    @Autowired
    private InstituteRepository instituteRepository;

    @Autowired
    private QualificationRepository qualificationRepository;

    @Test
    void getJobSeeker() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(89L).orElseThrow();
        System.out.println(jobSeeker);
    }

    @Test
    void setQualification() {
        JobSeeker jobSeeker = jobSeekerRepository.findById(117L).orElseThrow();
        Degree degree = degreeRepository.findById(14L).orElseThrow();
        Institute institute = instituteRepository.findById(13L).orElseThrow();
        Qualification qualification = Qualification.builder()
                                                    .degree(degree)
                                                    .institute(institute)
                                                    .startYear("2023")
                                                    .endYear("2025")
                                                    .build();
        qualification = qualificationRepository.save(qualification);
        List<Qualification> qualifications = jobSeeker.getQualifications();
        qualifications.add(qualification);
        jobSeeker.setQualifications(qualifications);
        jobSeekerRepository.save(jobSeeker);
    }

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
        jobSeeker.setTitle(NameTitle.MR);
        jobSeeker.setFirstName("Indrajith");
        jobSeeker.setLastName("Madhumal");
        jobSeekerRepository.save(jobSeeker);
    }
}
