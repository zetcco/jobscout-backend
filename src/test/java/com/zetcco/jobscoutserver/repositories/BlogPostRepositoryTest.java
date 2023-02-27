package com.zetcco.jobscoutserver.repositories;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.domain.support.User;

@SpringBootTest
public class BlogPostRepositoryTest {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void saveBlogPost() {
        User user = userRepository.findById(1L).orElseThrow();
        BlogPost blogPost = BlogPost.builder()
                .user(user)
                .content("WTF")
                .timeStamp(new Date())
                .build();

        blogPostRepository.save(blogPost);
    }

    @Test
    void updateBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(2L).orElseThrow();

        blogPost.setContent("WTF Updated");
        blogPost.setTimeStamp(new Date());

        blogPostRepository.save(blogPost);
    }

    @Test
    void deleteBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(2L).orElseThrow();

        blogPostRepository.delete(blogPost);
    }

    @Test
    void getBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(2L).orElseThrow();

        System.out.println("\n\n=============================================================\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n=============================================================\n\n");

    }
}
