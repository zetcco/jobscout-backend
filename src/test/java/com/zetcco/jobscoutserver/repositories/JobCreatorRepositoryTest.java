package com.zetcco.jobscoutserver.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.JobCreator;
import com.zetcco.jobscoutserver.domain.support.Address;
import com.zetcco.jobscoutserver.domain.support.NameTitle;

@SpringBootTest
public class JobCreatorRepositoryTest {

    @Autowired
    private JobCreatorRepository jobCreatorRepository;

    @Test
    public void saveJobCreator() {
        Address address = Address.builder()
                                .number("41")
                                .street("Street")
                                .town("Town")
                                .city("City")
                                .province("Province")
                                .country("Country")
                                .build();
        JobCreator jobCreator = new JobCreator("create@gmail.com", "createpasswd", address);
        jobCreatorRepository.save(jobCreator);
    }

    @Test
    public void saveJobCreatorProfile() {
        JobCreator jobCreator = jobCreatorRepository.findById(1L).orElseThrow();
        jobCreator.setTitle(NameTitle.MR);
        jobCreator.setFirstName("Sanjan");
        jobCreator.setLastName("Madushan");
        jobCreatorRepository.save(jobCreator);
    }
}
