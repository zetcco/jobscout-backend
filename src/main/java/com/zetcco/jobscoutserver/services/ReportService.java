package com.zetcco.jobscoutserver.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.zetcco.jobscoutserver.domain.Report;
import com.zetcco.jobscoutserver.repositories.ReportRepository;
import com.zetcco.jobscoutserver.services.mappers.ReportMapper;
import com.zetcco.jobscoutserver.services.support.ReportDTO;

@Service
public class ReportService {

    @Autowired private ReportMapper reportMapper;
    @Autowired private ReportRepository reportRepository;
    @Autowired private UserService userService;

    public ReportDTO fileReport(ReportDTO reportDTO) {
        Report report = new Report(
            null,
            reportDTO.getType(),
            userService.getAuthUser(),
            reportDTO.getContentId(),
            new Date(),
            reportDTO.getMessage()
        );
        report = reportRepository.save(report);
        return reportMapper.mapToDto(report);
    }

    public List<ReportDTO> getReports() {
        List<Report> reports = reportRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        return reportMapper.mapDtos(reports);
    }

    public void deleteReport(Long reportId) {
        reportRepository.deleteById(reportId);
    }
    
}
