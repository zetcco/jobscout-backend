package com.zetcco.jobscoutserver.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.services.ReportService;
import com.zetcco.jobscoutserver.services.support.ReportDTO;

@Controller
@RequestMapping("/report")
public class ReportController {

    @Autowired ReportService reportService;

    @PostMapping("/add")
    public ResponseEntity<ReportDTO> fileReport(
        @RequestBody ReportDTO reportDTO
    ) {
        try {
            return new ResponseEntity<ReportDTO>(reportService.fileReport(reportDTO), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<ReportDTO>> getReports() {
        try {
            return new ResponseEntity<List<ReportDTO>>( reportService.getReports() , HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(("/delete/{reportId}"))
    public ResponseEntity<List<ReportDTO>> deleteReports(@PathVariable Long reportId) {
        try {
            reportService.deleteReport(reportId);
            return new ResponseEntity<List<ReportDTO>>(HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
