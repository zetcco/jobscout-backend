package com.zetcco.jobscoutserver.services;

import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void testSetSocialLinks() throws URISyntaxException {
        List<String> links = List.of(
            "https://github.com/zetcco",
            "www.github.com/zetcco",
            "github.com/zetcco",
            "http://facebook.com/zetcco",
            "www.hackerrank.com/zetcco",
            "linkedin.com/in/zetcco"
        );
        userService.setSocialLinks(links);
    }

}
