package com.zetcco.jobscoutserver.services.support;

import java.util.Date;

import com.zetcco.jobscoutserver.domain.support.ReportType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDTO {
    
    private Long id;
    private ReportType type;
    private String message;
    private ProfileDTO user;
    private Long contentId;
    private Date timeStamp;

}
