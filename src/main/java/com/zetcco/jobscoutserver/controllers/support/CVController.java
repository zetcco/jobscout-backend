package com.zetcco.jobscoutserver.controllers.support;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.UserService;
import com.zetcco.jobscoutserver.services.support.CVService;

@Controller
@RequestMapping("/cv")
public class CVController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private CVService cvService;
    
    @GetMapping("/generate/{templateId}")
    @PreAuthorize("hasRole('JOB_SEEKER')")
    public ResponseEntity<Resource> generateCv(@PathVariable Long templateId) {
        Long requesterId = userService.getUser().getId();
        try {
            Resource cvPdf = cvService.generateCV(requesterId, templateId);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Disposition", String.format("cv-%d-%d.pdf", requesterId, templateId));
            responseHeaders.set("Content-Type", "application/pdf");
            return ResponseEntity.ok().headers(responseHeaders).body(cvPdf);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/templates")
    public ResponseEntity<Object> getCvTemplates() {
        try {
            return new ResponseEntity<Object>(cvService.getCVTemplates(), HttpStatus.OK);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/templates/{templateId}/preview.jpg")
    public ResponseEntity<Resource> getPreviewImage(@PathVariable Long templateId) {
        try {
            Resource previewImage = cvService.getTemplatePreview(templateId);
            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.set("Content-Type", "image/jpeg");
            return ResponseEntity.ok().headers(responseHeaders).body(previewImage);
        } catch (RestClientException | URISyntaxException e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
