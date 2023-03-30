package com.zetcco.jobscoutserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.CodeExecutorService;
import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmission;
import com.zetcco.jobscoutserver.services.support.CodeExecutor.CodeSubmissionResult;

@Controller
@RequestMapping("/code")
public class CodeExecutorController {

    @Autowired
    private CodeExecutorService executorService;

    @PostMapping("/submit")
    public ResponseEntity<CodeSubmissionResult> submitCode(@RequestBody CodeSubmission submission) {
        try {
            return new ResponseEntity<CodeSubmissionResult>(executorService.submit(submission), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
}
