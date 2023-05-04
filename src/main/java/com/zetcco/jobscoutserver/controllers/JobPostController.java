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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.support.JobPostStatus;
import com.zetcco.jobscoutserver.domain.support.JobPostType;
import com.zetcco.jobscoutserver.domain.support.dto.JobPostDTO;
import com.zetcco.jobscoutserver.services.JobPostService;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Controller
@RequestMapping(value = "/jobpost")
public class JobPostController {

    @Autowired
    private JobPostService jobPostService;

    @GetMapping
    public ResponseEntity<List<JobPostDTO>> getAllJobPosts(@RequestParam("page") int page, @RequestParam("size") int size){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getAllJobPosts(page, size) , HttpStatus.OK);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/jobPostId")
    public ResponseEntity<JobPostDTO> getJobPostById(@RequestParam("val") Long jobPostId){
        try{
            return new ResponseEntity<JobPostDTO>(jobPostService.getJobPostById(jobPostId) , HttpStatus.OK);
        } catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/jobCreatorId")
    public ResponseEntity<List<JobPostDTO>> getJobPostByJobCreatorId(@RequestParam("val") Long jobCreatorId){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostsByJobCreatorId(jobCreatorId) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getJobPostCountByJobCreatorId(){
        try{
            return new ResponseEntity<Long>(jobPostService.getJobPostCount() , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/count/activated")
    public ResponseEntity<Long> getActivatedJobPostCountByJobCreatorId(){
        try{
            return new ResponseEntity<Long>(jobPostService.getActivatedJobPostCount() , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/count/deactivated")
    public ResponseEntity<Long> getDeactivatedJobPostCountByJobCreatorId(){
        try{
            return new ResponseEntity<Long>(jobPostService.getDeactivatedJobPostCount() , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/count/holded")
    public ResponseEntity<Long> getHoldedJobPostCountByJobCreatorId(){
        try{
            return new ResponseEntity<Long>(jobPostService.getHoldedJobPostCount() , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/organization/{organizationId}")
    public ResponseEntity<List<JobPostDTO>> getJobPostByOrganizationId(@PathVariable Long organizationId){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostsByOrganizationId(organizationId) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<JobPostDTO>> getJobPostByCategoryId(@PathVariable Long categoryId){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostsByCategoryId(categoryId) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/type")
    public ResponseEntity<List<JobPostDTO>> getJobPostByType(@RequestParam("val") JobPostType type){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostByType(type) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }
 
    @GetMapping("/status")
    public ResponseEntity<List<JobPostDTO>> getJobPostByStatus(@RequestParam("val") JobPostStatus status){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostByStatus(status) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @GetMapping("/Search")
    public ResponseEntity<List<JobPostDTO>> getJobPostNameByFTS(@PathVariable String desc , @RequestParam ("page") int page , @RequestParam ("size") int size){
        try{
            return new ResponseEntity<List<JobPostDTO>>(jobPostService.getJobPostByNameFTS(desc, page, size) , HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<JobPostDTO> saveJobPost(@RequestBody JobPostDTO jobPostDTO){
        try{
            return new ResponseEntity<JobPostDTO>(jobPostService.addNewJobPost(jobPostDTO) , HttpStatus.OK);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

    @PutMapping("/{jobPostId}")
    public ResponseEntity<JobPostDTO> updateJobPost(@PathVariable Long jobPostId , @RequestBody JobPostDTO jobPostDTO){
        try{
            if(jobPostId != jobPostDTO.getId())
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Incorrect parameters!");
            return new ResponseEntity<JobPostDTO>(jobPostService.updateJobPost(jobPostDTO), HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
        
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<String> deleteJobPostById(@PathVariable Long jobPostId){
        try{
            jobPostService.deleteJobPostById(jobPostId);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }catch(NotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND , e.getMessage());
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , e.getMessage());
        }
    }

}