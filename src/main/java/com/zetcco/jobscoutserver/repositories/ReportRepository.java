package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    
}
