package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.Meeting;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    
}
