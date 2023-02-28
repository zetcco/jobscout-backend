package com.zetcco.jobscoutserver.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.zetcco.jobscoutserver.domain.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    // Page<Notification> findByIdOrderByTimeStampDesc(Long id, Pageable page);
    Page<Notification> findByUserIdOrderByTimestampDesc(Long id, Pageable page);
    
}
