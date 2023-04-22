package com.zetcco.jobscoutserver.controllers;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.services.BlogPostService;
import com.zetcco.jobscoutserver.services.support.BlogPostDTO;

@Controller
@RequestMapping("/posts")
public class BlogPostController {

    @Autowired
    private BlogPostService blogPostService;

    @GetMapping
    public ResponseEntity<List<BlogPost>> getBlogPosts(@RequestParam("pageno") int page,
            @RequestParam("size") int size) {
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

    @PutMapping("/update")
    public ResponseEntity<BlogPostDTO> updateBlogPost(@RequestBody BlogPostDTO newBlogPostDTO) {
        try {
            return new ResponseEntity<BlogPostDTO>(blogPostService.updateBlogPost(newBlogPostDTO), HttpStatus.OK);
        } catch (AccessDeniedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    // @DeleteMapping("/delete")
    // public ResponseEntity<BlogPostDTO> deleteBlogPost(@RequestBody Map<String,Long> deleteBlogPost)
    //     try{
    //         return new ResponseEntity<>(blogPostService.deleteBlogPost(deleteBlogPost), HttpStatus.OK);
    //     } catch (Exception e) {
    //         throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    //     }

    // }

    @DeleteMapping("/delete")
    public ResponseEntity<BlogPostDTO> deleteBlogPost(@RequestBody BlogPostDTO deleteBlogPostDTO)
    {
        try{
            return new ResponseEntity<>(blogPostService.deleteBlogPost(deleteBlogPostDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @PostMapping("/add")
    public ResponseEntity<BlogPostDTO> saveBlogPost(@RequestBody BlogPostDTO blogPostDTO) {
        try {
            return new ResponseEntity<BlogPostDTO>(blogPostService.saveBlogPost(blogPostDTO), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }
}