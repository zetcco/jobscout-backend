package com.zetcco.jobscoutserver.repositories;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        User user = userRepository.findById(3L).orElseThrow();
        BlogPost blogPost = BlogPost.builder()
                .user(user)
                .content("post06")
                .timeStamp(new Date())
                .build();

        blogPostRepository.save(blogPost);
    }

    @Test
    void updateBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(4L).orElseThrow();

        blogPost.setContent("post03");
        blogPost.setTimeStamp(new Date());

        blogPostRepository.save(blogPost);
    }

    @Test
    void deleteBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(3L).orElseThrow();

        blogPostRepository.delete(blogPost);
    }

    @Test
    void getBlogPost() {
        BlogPost blogPost = blogPostRepository.findById(4L).orElseThrow();

        System.out.println("\n\n=============================================================\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n=============================================================\n\n");

    }

    @Test
    void getAllBlogPost() {
        Pageable page1 = PageRequest.of(0, 2);

        List<BlogPost> blogPosts = blogPostRepository.findAll(page1).getContent();

        System.out.println("\n\n=============================================================\n\n");

        for (BlogPost blogPost : blogPosts) {
            System.out.println(blogPost);
        }

        System.out.println("\n\n------------------------------n\n");

        Pageable page2 = PageRequest.of(1, 2);

        blogPosts = blogPostRepository.findAll(page2).getContent();

        System.out.println("\n\n------------------------------n\n");

        for (BlogPost blogPost : blogPosts) {
            System.out.println(blogPost);
        }

        System.out.println("\n\n=============================================================\n\n");

        /*
         * System.out.println(
         * "\n\n=============================================================\n\n");
         * 
         * for (BlogPost blogpost : blogPosts) {
         * System.out.println(blogpost);
         * 
         * }
         * System.out.println(
         * "\n\n=============================================================\n\n");
         */
    }

}
