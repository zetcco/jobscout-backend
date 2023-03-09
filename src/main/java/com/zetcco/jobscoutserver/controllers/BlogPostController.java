package com.zetcco.jobscoutserver.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.services.BlogPostService;

@Controller
@RequestMapping("/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPost>> getBlogPosts(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            return new ResponseEntity<List<BlogPost>>(blogPostService.getBlogPosts(page, size), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/{postId}")
    public ResponseEntity<BlogPost> getBlogPost(@PathVariable Long postId) {
        try {
            BlogPost blogPost = blogPostService.getBlogPost(postId);
            return new ResponseEntity<BlogPost>(blogPost, HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/update")
    public ResponseEntity<BlogPost> updateBlogPost(Long id, String content, Date date) {

        try {
            BlogPost blogPost = blogPostService.getBlogPost(id);
            System.out.println("Deleted Post :" + blogPost);
            return new ResponseEntity<BlogPost>(blogPostService.saveBlogPost(content, date), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/delete")
    public ResponseEntity<BlogPost> deleteBlogPost(Long id) {
        try {
            return new ResponseEntity<>(blogPostService.deleteBlogPost(id), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @GetMapping("/add")
    public ResponseEntity<BlogPost> saveBlogPost(String content, Date date) {
        try {
            return new ResponseEntity<BlogPost>(blogPostService.saveBlogPost(content, date), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}
