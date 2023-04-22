package com.zetcco.jobscoutserver.services;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.BlogPost;
import com.zetcco.jobscoutserver.domain.support.User;
import com.zetcco.jobscoutserver.repositories.BlogPostRepository;
import com.zetcco.jobscoutserver.repositories.UserRepository;
import com.zetcco.jobscoutserver.services.mappers.UserMapper;
import com.zetcco.jobscoutserver.services.support.BlogPostDTO;
import com.zetcco.jobscoutserver.services.support.NotFoundException;

import jakarta.transaction.Transactional;

@Service
public class BlogPostService {

    @Autowired
    private BlogPostRepository blogPostRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public List<BlogPost> getBlogPosts(int pageNo, int pageSize) {
        Pageable page = PageRequest.of(pageNo, pageSize);
        blogPostRepository.findAll(page).getContent();
    }

    public BlogPost getBlogPost(Long id) {
        BlogPost blogPost = blogPostRepository.findById(id).orElseThrow();
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        System.out.println(blogPost);
        System.out.println("\n\n--------------------------------------------------------------\n\n");
        return blogPost;
    }

    @Transactional
    public BlogPostDTO saveBlogPost(BlogPostDTO blogPostDTO) throws NotFoundException {

        Long userId = userService.getAuthUser().getId();
        User user = userRepository.findById(userId).orElseThrow();

        BlogPost blogPost = new BlogPost();
        blogPost.setContent(blogPostDTO.getContent());
        blogPost.setTimeStamp(new Date());
        blogPost.setUser(user);

        blogPost = blogPostRepository.save(blogPost);
        return this.mapToDto(blogPost);
    }

    @Transactional
    public BlogPostDTO updateBlogPost(BlogPostDTO blogPostDTO) throws AccessDeniedException {
        BlogPost blogPost = blogPostRepository.findById(blogPostDTO.getId())
                .orElseThrow(() -> new NotFoundException("Blog post not found"));
        if (userService.getAuthUser().getId() != blogPost.getUser().getId())
            throw new AccessDeniedException("You do not have permission to perform this action");
        blogPost.setContent(blogPostDTO.getContent());
        blogPost.setTimeStamp(new Date());
        blogPost = blogPostRepository.save(blogPost);
        return this.mapToDto(blogPost);
    }

    @Transactional
    public BlogPostDTO deleteBlogPost(BlogPostDTO blogPostDTO) throws AccessDeniedException {
        BlogPost blogPost = blogPostRepository.findById(blogPostDTO.getId())
                .orElseThrow(() -> new NotFoundException("Blog post not found"));
        if (userService.getAuthUser().getId() != blogPost.getUser().getId())
            throw new AccessDeniedException("You do not have permission to perform this action");

        blogPostRepository.delete(blogPost);
        return this.mapToDto(blogPost);
    }

    private BlogPostDTO mapToDto(BlogPost blogPost) {
        BlogPostDTO blogPostDTO = new BlogPostDTO(
                blogPost.getId(),
                userMapper.mapToDto(blogPost.getUser()), blogPost.getTimeStamp(), blogPost.getContent());
        return blogPostDTO;
    }

}
