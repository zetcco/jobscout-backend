package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.repositories.BlogPostRepository;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserService userService;

    public List<BlogPost> getBlogPosts(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize);
        return blogPostRepository.findAll(page).getContent();
    }

    public BlogPost getBlogPost(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        return blogPost;
    }

    public BlogPost updateBlogPost(Long id, String content, Date date) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();

        blogPost.setContent(content);
        blogPost.setTimeStamp(date);

        System.out.println("\n\n--------------------------------------------------------------\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        return blogPostRepository.save(blogPost);
    }

    public BlogPost saveBlogPost(String content) {
        BlogPost blogPost = new BlogPost();
        blogPost.setContent(content);
        blogPost.setTimeStamp(new Date());
        // blogPost.setUser(userService.get);

        System.out.println("\n\n--------------------------------------------------------------\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        return blogPostRepository.save(blogPost);
    }

    public BlogPost deleteBlogPost(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();

        System.out.println("\n\n--------------------------------------------------------------\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n--------------------------------------------------------------\n\n");

        blogPostRepository.delete(blogPost);
        return blogPost;
    }

}
