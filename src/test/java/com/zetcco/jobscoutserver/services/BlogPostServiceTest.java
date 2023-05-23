// package com.zetcco.jobscoutserver.services;

// import java.util.Date;

// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;

// import org.springframework.boot.test.context.SpringBootTest;

// import com.zetcco.jobscoutserver.domain.BlogPost;


// @SpringBootTest
// public class BlogPostServiceTest {

//     @Autowired
//     private BlogPostService blogPostService;

//     @Test
//     void getBlogPosts() {
//         for (BlogPost post : blogPostService.getBlogPosts(0, 2)) {
//             System.out.println(post);
//         }

//         System.out.println("---------------------");

//         for (BlogPost post : blogPostService.getBlogPosts(1, 2)) {
//             System.out.println(post);
//         }

//     }

//     @Test
//     void getBlogPost() {
//         BlogPost blogPost = blogPostService.getBlogPost(2L);
//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//         System.out.println(blogPost);
//         System.out.println("\n\n--------------------------------------------------------------\n\n");

//     }

//     @Test
//     void updateBlogPost() {
//         BlogPost blogPost = blogPostService.getBlogPost(3L);

//         blogPost.setContent("postupdated");
//         blogPost.setTimeStamp(new Date());

//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//         System.out.println(blogPost);
//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//         // blogPostService.saveBlogPost(BlogPost.getContent(), blogPost.getTimeStamp());
//         System.out.println(blogPost.getContent());
//         System.out.println(blogPost.getTimeStamp());
//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//     }

//     @Test
//     void saveBlogPost() {
//         BlogPost blogPost = new BlogPost();
//         blogPost.setContent("post011");
//         blogPost.setTimeStamp(new Date());

//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//         System.out.println(blogPost);
//         System.out.println("\n\n--------------------------------------------------------------\n\n");
//         // blogPostService.saveBlogPost(blogPost.getContent(), blogPost.getTimeStamp());
//     }

//     // @Test
//     // void deleteBlogPost() {
//     //     BlogPost blogPost = blogPostService.getBlogPost(2L);
//     //     blogPostService.deleteBlogPost(2L);
//     //     System.out.println("Deleted Post :" + blogPost);

//     // }
// }
