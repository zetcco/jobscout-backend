package com.zetcco.jobscoutserver.domain;

import java.util.Date;

import com.zetcco.jobscoutserver.domain.support.ReportType;
import com.zetcco.jobscoutserver.domain.support.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private ReportType type;

    @OneToOne
    private User reporter;

    private Long contentId;

    private Date timestamp;
    
    @Column(columnDefinition = "TEXT")
    private String message;

}
