package com.zetcco.jobscoutserver.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CodeExecutorServiceTest {

    @Autowired
    private CodeExecutorService executorService;

    @Test
    void testGet() {
        executorService.getTest();
    }

    @Test
    void testSubmission() {
        executorService.postTest();
    }
}
