package com.zetcco.jobscoutserver.controllers;

import java.util.List;

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
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.services.JobPostService;

@Controller
@RequestMapping(value = "/jobPost")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @GetMapping("/")
    public ResponseEntity<List<JobPostDTO>> getAllJobPosts(){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getAllJobPosts() , HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/{jobPostId}")
    public ResponseEntity<JobPostDTO> getJobPostById(@PathVariable Long Id){
        try{
            return new ResponseEntity<JobPostDTO>(jobPostService.getJobPostById(Id) , HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/Type")
    public ResponseEntity<List<JobPostDTO>> getJobPostByType(@PathVariable("type") JobPostType type){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostByType(type) , HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }
 
    @GetMapping("/Status")
    public ResponseEntity<List<JobPostDTO>> getJobPostByStatus(@PathVariable("status") JobPostStatus status){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostByStatus(status) , HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<JobPostDTO> saveJobPost(@RequestBody JobPostDTO jobPostDTO){
        try{
            return new ResponseEntity<JobPostDTO>(jobPostService.addNewJobPost(jobPostDTO) , HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @PutMapping("/{JobPostId}")
    public ResponseEntity<JobPostDTO> updateJobPost(@PathVariable Long jobPostId , @RequestBody JobPostDTO jobPostDTO){
        try{
            if(jobPostId != jobPostDTO.getId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Incorrect parameters");
            return new ResponseEntity<JobPostDTO>(jobPostService.updateJobPost(jobPostId, jobPostDTO), HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
        
        }

    @DeleteMapping("/")
    public void deleteJobPostById(@PathVariable Long JobPostId , @RequestBody JobPostDTO jobPostDTO){
        try{
            if(JobPostId != jobPostDTO.getId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect parameters");
            jobPostService.deleteJobPostById(JobPostId, jobPostDTO);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    }