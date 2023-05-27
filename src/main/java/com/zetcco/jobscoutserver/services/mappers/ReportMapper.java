package com.zetcco.jobscoutserver.services.mappers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zetcco.jobscoutserver.domain.Report;
import com.zetcco.jobscoutserver.services.support.ReportDTO;

@Component
public class ReportMapper {
    
    @Autowired private UserMapper userMapper;

    public ReportDTO mapToDto(Report report) {
        return new ReportDTO(
            report.getId(),
            report.getType(),
            report.getMessage(),
            userMapper.mapToDto(report.getReporter()),
            report.getContentId(),
            report.getTimestamp()
        );
    }

    public List<ReportDTO> mapDtos(List<Report> reports) {
        return reports.stream().map( report -> this.mapToDto(report) ).toList();
    }

}
